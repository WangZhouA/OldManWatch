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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.DelDevice;
import com.saiyi.oldmanwatch.entity.DeviceState;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;
import com.saiyi.oldmanwatch.entity.UpdateDeviceBean;
import com.saiyi.oldmanwatch.entity.UploadImgBean;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.mvp.presenter.MyDevicePresenter;
import com.saiyi.oldmanwatch.mvp.view.MyDeviceView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.FileUtil;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class MyPositionActivity extends BaseMvpAppCompatActivity<MyDevicePresenter> implements MyDeviceView<DeviceState> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.civ_pic)
    CircleImageView civ_pic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_set_disturb)
    TextView tvSetDisturb;
    @BindView(R.id.ll_family_member)
    LinearLayout llFamilyMember;
    @BindView(R.id.tv_family_number)
    TextView tvFamilyNumber;
    @BindView(R.id.tv_situational_mode)
    TextView tvSituationalMode;
    @BindView(R.id.tv_del_device)
    TextView tvDelDevice;


    private Context mContext;
    private String token;
    private String mac;
    private int uid;
    private String type;
    private File file;
    private Uri imgUriOri;
    private String imgurl;
    private String filiation;
    private String headUrl;
    private static final String TAG = "MyWatchActivity";
    private static final int CAMERA_RESULT_CODE = 0;
    private static final int CROP_RESULT_CODE = 1;
    private static final int ALBUM_RESULT_CODE = 2;
    private static final int REQUEST_PERMISSIONS = 3;
    private String urlpath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_position;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
    }

    @Override
    protected MyDevicePresenter createPresenter() {
        return new MyDevicePresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
        token = (String) SharedPreferencesHelper.get("token", "");
        uid = (int) SharedPreferencesHelper.get("uid", 0);
        type = getIntent().getStringExtra("type");
        filiation = getIntent().getStringExtra("filiation");
        headUrl = getIntent().getStringExtra("headUrl");
        Glide.with(mContext).load(Constant.getRoot() + headUrl).error(Util.getRes(filiation)).into(civ_pic);
        tvName.setText(filiation);
    }

    @OnClick({R.id.iv_back, R.id.civ_pic, R.id.tv_set_disturb,
            R.id.tv_situational_mode, R.id.tv_family_number, R.id.ll_family_member,
            R.id.tv_del_device})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.civ_pic:
                showPopwindow();
                break;
            case R.id.tv_set_disturb:
                intent.setClass(mContext, DisturbActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.tv_situational_mode:
                intent.setClass(mContext, ModelSetActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.tv_family_number:
                intent.setClass(mContext, SosActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.ll_family_member:
                intent.setClass(mContext, FamilyMemberActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.tv_del_device:

                mPresenter.delMyDevice(this);
                break;
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
                        civ_pic.setImageBitmap(bitmap);
                        // 把裁剪后的图片保存至本地 返回路径
                        urlpath = FileUtil.saveFile(mContext, "crop.jpg", bitmap);
                        Logger.e(TAG, "裁剪图片地址->" + urlpath);
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
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "删除成功！", ToastUtils.LENGTH_LONG);
            finish();
        }
    }


    @Override
    public void getCode(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "设置成功！", ToastUtils.LENGTH_LONG);
            finish();
        }
    }

    @Override
    public void getImgUrl(String url) {
        imgurl = url;
        mPresenter.updateDevice(this);
    }

    @Override
    public String getUrl() {
        return urlpath;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public SwitchSetBean getSwitchData() {
        return null;
    }

    @Override
    public DelDevice getDevice() {
        DelDevice data = new DelDevice();
        data.setMac(mac);
        data.setUid(uid);
        return data;
    }

    @Override
    public UpdateDeviceBean getData() {
        UpdateDeviceBean device = new UpdateDeviceBean();
        device.setFiliation(filiation);
        device.setHeadUrl(imgurl);
        device.setMac(mac);
        device.setUid(uid);
        device.setRelationName("");
        return device;
    }


    @Override
    public void onRequestSuccessData(DeviceState data) {

    }
}
