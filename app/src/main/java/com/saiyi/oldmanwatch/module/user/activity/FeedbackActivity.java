package com.saiyi.oldmanwatch.module.user.activity;

import android.Manifest;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.AddOpinionBean;
import com.saiyi.oldmanwatch.entity.UploadImgBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.mvp.presenter.FeedbackPresenter;
import com.saiyi.oldmanwatch.mvp.view.FeedbackView;
import com.saiyi.oldmanwatch.utils.FileUtil;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/29
 * @Describe 意见反馈
 */
public class FeedbackActivity extends BaseMvpAppCompatActivity<FeedbackPresenter> implements FeedbackView<BaseResponse> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.rl_txt)
    RelativeLayout rlTxt;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.iv_add_pic)
    ImageView ivAddPic;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context mContext;
    private static final int CAMERA_RESULT_CODE = 0;
    private static final int CROP_RESULT_CODE = 1;
    private static final int ALBUM_RESULT_CODE = 2;
    private static final int REQUEST_PERMISSIONS = 3;
    private static final String TAG = "FeedbackActivity";
    private String imgurl = "";
    private String imgPathOri;
    private int uid;
    private File file;
    private Uri imgUriOri;
    private String type;

    @Override
    protected int getLayoutId() {
        type = (String) SharedPreferencesHelper.get("type", "");
        if ("2".equals(type))
            return R.layout.activity_position_feedback;
        else
            return R.layout.activity_feedback;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarTitle.setText(getResources().getString(R.string.feedback));
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvConfirm.setClickable(false);
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getEditText();
            }
        });
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter(this);
    }

    @Override
    protected void initData() {
        uid = (int) SharedPreferencesHelper.get("uid", -1);
    }

    @OnClick({R.id.tv_BarLeft, R.id.iv_add_pic, R.id.tv_confirm, R.id.rl_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.iv_add_pic:
                showPopwindow();
                break;
            case R.id.tv_confirm:

                mPresenter.addOpinion(this);
                break;
            case R.id.rl_txt:
                etText.requestFocus();
                showKeyboard();
                break;
        }
    }

    public void getEditText() {
        String txt = etText.getText().toString();
        if ("".equals(txt)) {
            tvConfirm.setBackgroundResource(R.drawable.btn_gray_shape);
            tvConfirm.setClickable(false);
        } else {
            if ("2".equals(type))
                tvConfirm.setBackgroundResource(R.drawable.btn_orange_shape);
            else
                tvConfirm.setBackgroundResource(R.drawable.btn_blue_shape);
            tvConfirm.setClickable(true);
        }
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
                Intent intent = new Intent();
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
     * 弹出软键盘
     */
    private void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(etText, 0);
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
                        ivAddPic.setImageBitmap(bitmap);
                        // 把裁剪后的图片保存至本地 返回路径
                        String urlpath = FileUtil.saveFile(mContext, "crop.jpg", bitmap);
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
        imgPathOri = image.getAbsolutePath();
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
                    Log.e(TAG, "用户授权相机权限");
                    openSysCamera();
                } else {
                    // 勾选了不再询问
                    Log.e(TAG, "用户拒绝相机权限");
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
    public void getImgUrl(String url) {
        imgurl = url;
    }

    @Override
    public AddOpinionBean getData() {
        String txt = etText.getText().toString();
        AddOpinionBean data = new AddOpinionBean();
        data.setUid(uid);
        data.setContents(txt);
        data.setImgUrl(imgurl);
        return data;
    }

    @Override
    public String getUrl() {
        return imgurl;
    }

    @Override
    public void onRequestSuccessData(BaseResponse data) {
        if ("1000".equals(data.getCode())) {
            ToastUtils.show(mContext, "提交成功！", ToastUtils.LENGTH_LONG);
            finish();
        }
    }
}
