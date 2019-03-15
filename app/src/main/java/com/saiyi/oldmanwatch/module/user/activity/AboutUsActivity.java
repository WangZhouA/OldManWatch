package com.saiyi.oldmanwatch.module.user.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/29
 * @Describe 关于我们
 */
public class AboutUsActivity extends BaseAppCompatActivity {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_permit)
    TextView tvPermit;
    @BindView(R.id.tv_problem)
    TextView tvProblem;
    @BindView(R.id.tv_tel)
    TextView tvTel;

    private Context mContext;
    private static final int REQUEST_CODE_ASK_CALL_PHONE = 1;
    private String type;

    @Override
    protected int getLayoutId() {
        type = (String) SharedPreferencesHelper.get("type", "");
        if ("2".equals(type))
            return R.layout.activity_position_about_us;
        else
            return R.layout.activity_about_us;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(getResources().getString(R.string.about_us));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_permit, R.id.tv_problem, R.id.tv_tel})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_permit:

                break;
            case R.id.tv_problem:

                break;
            case R.id.tv_tel:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_LOGS}, REQUEST_CODE_ASK_CALL_PHONE);
                }
                break;
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    //动态权限申请后处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                // Permission Granted callDirectly(mobile);

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                callPhone("");
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
