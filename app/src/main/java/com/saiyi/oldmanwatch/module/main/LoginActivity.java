package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.LoginBean;
import com.saiyi.oldmanwatch.entity.RequestLogin;
import com.saiyi.oldmanwatch.module.user.activity.ReSetPwdActivity;
import com.saiyi.oldmanwatch.mvp.presenter.LoginPresenter;
import com.saiyi.oldmanwatch.mvp.view.LoginView;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.WXShare;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @Author liwenbo
 * @Date 18/9/20
 * @Describe
 */
public class LoginActivity extends BaseMvpAppCompatActivity<LoginPresenter> implements LoginView<LoginBean> {
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.iv_see_pass)
    ImageView ivSeePass;
    @BindView(R.id.cb_emember)
    CheckBox cbEmemBer;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.iv_wxchat)
    ImageView ivWxchat;

    private Context mContext;
    /**
     * 微信登录相关
     */
    private IWXAPI api;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = LoginActivity.this;
        tvLogin.setClickable(false);
        etAccount.addTextChangedListener(new TextWatcher() {
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
        etPass.addTextChangedListener(new TextWatcher() {
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
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initData() {
        //通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, WXShare.APP_ID, true);
        //将应用的appid注册到微信
        api.registerApp(WXShare.APP_ID);
    }

    @OnClick({R.id.iv_see_pass, R.id.tv_BarRight, R.id.tv_login, R.id.tv_forget_password, R.id.iv_wxchat})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_see_pass:
                break;
            case R.id.tv_login:
                mPresenter.login(this);
                break;
            case R.id.tv_BarRight:
                intent.setClass(mContext, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget_password:
                intent.setClass(mContext, ReSetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_wxchat:
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";//
//                req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信登录";
                api.sendReq(req);
                break;
        }
    }


    public void getEditText() {
        String phone = etAccount.getText().toString();
        String pass = etPass.getText().toString();
        if ("".equals(phone) || "".equals(pass)) {
            tvLogin.setBackgroundResource(R.drawable.btn_gray_shape);
            tvLogin.setClickable(false);
        } else {
            tvLogin.setBackgroundResource(R.drawable.btn_blue_shape);
            tvLogin.setClickable(true);
        }
    }

    @Override
    public RequestLogin getData() {
        String account = etAccount.getText().toString();
        String pass = etPass.getText().toString();
        RequestLogin login = new RequestLogin();
        login.setPhone(account);
        login.setPassword(pass);
        return login;
    }

    @Override
    public void onRequestSuccessData(LoginBean data) {
        if (cbEmemBer.isChecked()) {
            SharedPreferencesHelper.put("pass", etPass.getText().toString());
        }
        Logger.e("ssss", "data...." + data.toString());
        String headUrl = data.getHeadUrl() == null ? "" : data.getHeadUrl();
        String name = data.getName() == null ? "" : data.getName();
        ToastUtils.show(mContext, "登录成功！", ToastUtils.LENGTH_LONG);
        SharedPreferencesHelper.put("account", etAccount.getText().toString());
        SharedPreferencesHelper.put("isLogin", true);
        SharedPreferencesHelper.put("uid", data.getUid());
        SharedPreferencesHelper.put("headUrl", headUrl);
        SharedPreferencesHelper.put("name", name);
        SharedPreferencesHelper.put("mac", data.getMac() == null ? "" : data.getMac());
        SharedPreferencesHelper.put("token", data.getToken());
        SharedPreferencesHelper.put("type", data.getType() == null ? "1" : data.getType());
        SharedPreferencesHelper.put("filiation", data.getFiliation() == null ? "" : data.getFiliation());


        JPushInterface.setAlias(LoginActivity.this,etAccount.getText().toString().trim() ,new TagAliasCallback() {
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


        Intent intent = new Intent();
        if ("2".equals(data.getType())) {
            intent.setClass(mContext, PositionerMainActivity.class);
        }
        else if ("1".equals(data.getType())) {
            intent.setClass(mContext, MainActivity.class);
        }else {
            intent.setClass(mContext, ScalesActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
