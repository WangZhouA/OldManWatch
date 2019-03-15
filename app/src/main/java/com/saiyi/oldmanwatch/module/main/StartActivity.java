package com.saiyi.oldmanwatch.module.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.functions.Consumer;

/**
 * @Author liwenbo
 * @Date 18/9/19
 * @Describe 启动页面
 */
public class StartActivity extends BaseAppCompatActivity {

    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        new SharedPreferencesHelper(mContext);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_CALL_LOG,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
//                            ToastUtils.showToast(mContext, "App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                        }
                        //不管是否获取全部权限，进入主页面
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                boolean isLogin = (boolean) SharedPreferencesHelper.get("isLogin", false);
                                String type = (String) SharedPreferencesHelper.get("type", "");
                                if (!isLogin) {
                                    Intent in = new Intent(mContext, LoginActivity.class);
                                    startActivity(in);
                                    finish();
                                    SharedPreferencesHelper.put("isfer", false);
                                } else {

                                    JPushInterface.setAlias(StartActivity.this,(String) SharedPreferencesHelper.get("account", ""),new TagAliasCallback() {
                                        @Override
                                        public void gotResult(int requestCode, String s, Set<String> set) {
                                            int  requestCodeA= requestCode;
                                            Log.e("requestCodeA---------->", "====" + requestCodeA);
                                            switch (requestCode) {
                                                case 0:
                                                    Log.e("别名设置成功---------------->", "====" + s);
                                                    break;
                                                case 6002:
                                                    Log.e("别名设置失败---------------->", "====" + s);

                                                    break;
                                            }
                                        }
                                    });



                                    Intent in = new Intent();
                                    if ("2".equals(type))
                                        in.setClass(mContext, PositionerMainActivity.class);
                                    else in.setClass(mContext, ScalesActivity.class);
                                    startActivity(in);
                                    finish();
                                }
                            }
                        }, 3000);
                    }
                });
    }

    @Override
    protected void initData() {

    }


}
