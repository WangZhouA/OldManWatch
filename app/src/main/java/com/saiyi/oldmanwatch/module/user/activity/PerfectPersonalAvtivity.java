package com.saiyi.oldmanwatch.module.user.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.dialog.DialogListener;
import com.saiyi.oldmanwatch.dialog.DialogUtils;
import com.saiyi.oldmanwatch.entity.UpdateUserBean;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.mvp.presenter.PerfectPersonalPresenter;
import com.saiyi.oldmanwatch.mvp.view.PerfectPersonalView;
import com.saiyi.oldmanwatch.utils.BirthUtils;
import com.saiyi.oldmanwatch.utils.FileUtil;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.TimeUtil;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fule.com.mydatapicker.DataPickerDialog;
import fule.com.mydatapicker.DatePickerDialog;
import fule.com.mydatapicker.DateUtil;

/**
 * @Author liwenbo
 * @Date 18/10/23
 * @Describe 完善用户资料页面
 */
public class PerfectPersonalAvtivity extends BaseMvpAppCompatActivity<PerfectPersonalPresenter> implements PerfectPersonalView<String> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvHight)
    TextView tvHight;
    @BindView(R.id.imHightB)
    ImageView imHightB;
    @BindView(R.id.rl_height)
    RelativeLayout rlHeight;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.imSexB)
    ImageView imSexB;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tvbirth)
    TextView tvbirth;
    @BindView(R.id.imbirthB)
    ImageView imbirthB;
    @BindView(R.id.rl_birth)
    RelativeLayout rlBirth;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private ArrayList<String> mData = new ArrayList<>();
    private Context mContext;
    private String imgurl = "";
    private static final int CAMERA_RESULT_CODE = 0;
    private static final int CROP_RESULT_CODE = 1;
    private static final int ALBUM_RESULT_CODE = 2;
    private static final int REQUEST_PERMISSIONS = 3;
    private static final String TAG = "FeedbackActivity";
    private File file;
    private Uri imgUriOri;
    private String token;
    private String phone;
    private String type;
    private String urlpath;
    private Dialog dateDialog; // 时间
    private Dialog chooseDialog;  //性别
    private Dialog heightDialog;  //身高
    private List<String> heightlist = new ArrayList<>(); //身高

    @Override
    protected int getLayoutId() {
        type = (String) SharedPreferencesHelper.get("type", "");
        if ("2".equals(type))
            return R.layout.avtivity_position_perfect_personal;
        else
            return R.layout.avtivity_perfect_personal;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.user_info);
    }

    @Override
    protected PerfectPersonalPresenter createPresenter() {
        return new PerfectPersonalPresenter(this);
    }

    @Override
    protected void initData() {
        //填充身高
        for (int i = 50; i <= 300; i++) {
            heightlist.add(i +"");
        }
        mData.addAll(Arrays.asList(getResources().getStringArray(R.array.sexs)));
        token = (String) SharedPreferencesHelper.get("token", "");
        phone = (String) SharedPreferencesHelper.get("account", "");
        String age = getIntent().getStringExtra("age");
        String sex = getIntent().getStringExtra("sex");
        String headUrl = getIntent().getStringExtra("headUrl");
        String name = getIntent().getStringExtra("name");
        String birthday = getIntent().getStringExtra("birthday");
        String height = getIntent().getStringExtra("height");
        tvSex.setText(sex);
        tvbirth.setText(birthday);
        tvName.setText(name);
        tvHight.setText(height);
        Glide.with(mContext).load(Constant.getRoot() + headUrl).error(R.mipmap.head_portrait).into(civPic);
    }


    @OnClick({R.id.tv_BarLeft, R.id.civ_pic, R.id.rl_height, R.id.rl_sex, R.id.rl_birth, R.id.rl_name, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.civ_pic:
                showPopwindow();
                break;
            case R.id.rl_height:

                showChooseDialogHeight(heightlist);

                break;
            case R.id.rl_sex:

                showChooseDialog(mData);

                break;
            case R.id.rl_birth:
                showDateDialog(DateUtil.getDateForString(TimeUtil.getCurrentTime()));
                break;
            case R.id.rl_name:
                DialogUtils.showDialogEditText(this, R.string.edName, new DialogListener() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onCompleteDate(String str) {

                        tvName.setText(str);

                    }
                }, 0);

                break;
            case R.id.tv_confirm:
                mPresenter.updateUser(this);
                break;
        }
    }

    /**
     * 显示日期
     *
     * @param date
     */
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {


                tvbirth.setText(String.format("%d-%s-%s", dates[0], dates[1] > 9 ? dates[1] : ("0" + dates[1]), dates[2] > 9 ? dates[2] : ("0" + dates[2])));
            }

            @Override
            public void onCancel() {

            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);
        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }
    /**
     *  性别
     * */

    /**
     * 显示其他列表
     */
    private void showChooseDialog(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        chooseDialog = builder.setData(mlist).setSelection(1).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        tvSex.setText(itemValue);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
        chooseDialog.show();
    }

    private void showChooseDialogHeight(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        heightDialog = builder.setData(mlist).setSelection(1).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        tvHight.setText(itemValue);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
        heightDialog.show();
    }

    private void showPopwindow() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.camera_pop_menu, null);

        TextView tvDefault = (TextView) popView.findViewById(R.id.tv_camera_pop_default);
        TextView tvCamera = (TextView) popView.findViewById(R.id.tv_camera_pop_camera);
        TextView tvCancel = (TextView) popView.findViewById(R.id.tv_camera_pop_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels - Util.getStatusBarHeight(mContext);
        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_camera_pop_default:
                        openSysAlbum();
                        break;
                    case R.id.tv_camera_pop_camera:
                        initPermission();
                        break;
                    case R.id.tv_camera_pop_cancel:
                        popWindow.dismiss();
                        break;
                }
                popWindow.dismiss();
            }
        };

        tvDefault.setOnClickListener(listener);
        tvCamera.setOnClickListener(listener);
        tvCancel.setOnClickListener(listener);

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 打开系统相机
     */
    private void openSysCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        file = new File(Environment.getExternalStorageDirectory(), imgName);
        try {
            file = createOriImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imgUriOri = Uri.fromFile(file);
            } else {
                imgUriOri = FileProvider.getUriForFile(mContext, getPackageName() + ".provider", file);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri);
            startActivityForResult(cameraIntent, CAMERA_RESULT_CODE);
        }
    }


    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_RESULT_CODE:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    cropPic(Uri.fromFile(file));
                } else {
                    cropPic(getImageContentUri(file));
                }

                break;
            case CROP_RESULT_CODE:

                // 裁剪时,这样设置 cropIntent.putExtra("return-data", true); 处理方案如下
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        civPic.setImageBitmap(bitmap);
                        // 把裁剪后的图片保存至本地 返回路径
                        urlpath = FileUtil.saveFile(mContext, "crop.jpg", bitmap);
                        Log.e(TAG, "裁剪图片地址->" + urlpath);
                        mPresenter.upLoadImg(this);
                    }
                }
                break;
            case ALBUM_RESULT_CODE:
                // 相册
                cropPic(data.getData());
                break;
        }

    }

    /**
     * 创建原图像保存的文件
     *
     * @return
     * @throws IOException
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "HomePic_" + new SimpleDateFormat(
                "yyyyMMdd_HHmmss").format(new Date());
        File pictureDirOri = new File(getExternalFilesDir(
                Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/OriPicture");
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        File image = File.createTempFile(
                imgNameOri,
                ".png",
                pictureDirOri
        );
        return image;
    }

    /**
     * 裁剪图片
     *
     * @param data
     */
    private void cropPic(Uri data) {
        if (data == null) {
            return;
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(data, "image/*");

        // 开启裁剪：打开的Intent所显示的View可裁剪
        cropIntent.putExtra("crop", "true");
        // 裁剪宽高比
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // 裁剪输出大小
        cropIntent.putExtra("outputX", 320);
        cropIntent.putExtra("outputY", 320);
        cropIntent.putExtra("scale", true);
        /**
         * return-data
         * 这个属性决定我们在 onActivityResult 中接收到的是什么数据，
         * 如果设置为true 那么data将会返回一个bitmap
         * 如果设置为false，则会将图片保存到本地并将对应的uri返回，当然这个uri得有我们自己设定。
         * 系统裁剪完成后将会将裁剪完成的图片保存在我们所这设定这个uri地址上。我们只需要在裁剪完成后直接调用该uri来设置图片，就可以了。
         */
        cropIntent.putExtra("return-data", true);
        // 当 return-data 为 false 的时候需要设置这句
        //cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // 图片输出格式
        //cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 头像识别 会启动系统的拍照时人脸识别
        cropIntent.putExtra("noFaceDetection", true);
        startActivityForResult(cropIntent, CROP_RESULT_CODE);
    }

    /**
     * 7.0以上获取裁剪 Uri
     *
     * @param imageFile
     * @return
     */
    private Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 初始化相机相关权限
     * 适配6.0+手机的运行时权限
     */
    private void initPermission() {
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 之前拒绝了权限，但没有点击 不再询问 这个时候让它继续请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            } else {
                //注册相机权限
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            }
        } else {
            openSysCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //成功
                    Logger.e(TAG, "用户授权相机权限");
                    openSysCamera();
                } else {
                    // 勾选了不再询问
                    Logger.e(TAG, "用户拒绝相机权限");
                    /**
                     * 跳转到 APP 详情的权限设置页
                     * 可根据自己的需求定制对话框，点击某个按钮在执行下面的代码
                     */
                    Intent intent = Util.getAppDetailSettingIntent(mContext);
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public UpdateUserBean getData() {
        String name = tvName.getText().toString();
        String sex = tvSex.getText().toString();
        String age ="";

        try {
            age = BirthUtils.getAge(BirthUtils.parse(tvbirth.getText().toString()))+"";
        } catch (Exception e) {
            e.printStackTrace();
        }
        String height = tvHight.getText().toString();
        String birthday =tvbirth.getText().toString();
        UpdateUserBean data = new UpdateUserBean();
        data.setAge(age);
        data.setHeadUrl(imgurl);
        data.setName(name);
        data.setSex(sex);
        data.setBirthday(birthday);
        data.setHeight(height);
        data.setPhone(phone);
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "修改成功！", ToastUtils.LENGTH_LONG);
            finish();
        }else {

            ToastUtils.show(mContext, "修改失败！", ToastUtils.LENGTH_LONG);
        }
    }

    @Override
    public String getUrl() {
        return urlpath;
    }

    @Override
    public void onRequestSuccessData(String data) {
        imgurl = data;
        Glide.with(mContext).load(Constant.getRoot() + imgurl).error(R.mipmap.head_portrait).into(civPic);
    }
}
