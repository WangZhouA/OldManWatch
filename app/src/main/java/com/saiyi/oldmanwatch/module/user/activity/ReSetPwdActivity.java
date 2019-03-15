package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.UpdatePWD;
import com.saiyi.oldmanwatch.mvp.presenter.ReSetPwdPresenter;
import com.saiyi.oldmanwatch.mvp.view.ReSetPwdView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/28
 * @Describe 重置密码页面
 */
public class ReSetPwdActivity extends BaseMvpAppCompatActivity<ReSetPwdPresenter> implements ReSetPwdView<String> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_pass_again)
    EditText etPassAgain;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context mContext;
    private boolean isStop;
    private boolean isbreak;
    private String type;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x01) {
                tvGetCode.setText((60 - msg.arg1) + "S");
            } else if (msg.what == 0x02) {
                tvGetCode.setText("获取验证码");
                tvGetCode.setClickable(true);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        type = (String) SharedPreferencesHelper.get("type", "");
        if ("2".equals(type))
            return R.layout.activity_position_reset_pwd;
        else
            return R.layout.activity_reset_pwd;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(getResources().getString(R.string.reset_pwd));
        tvConfirm.setClickable(false);
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
        etCode.addTextChangedListener(new TextWatcher() {
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
        etPassAgain.addTextChangedListener(new TextWatcher() {
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
    protected ReSetPwdPresenter createPresenter() {
        return new ReSetPwdPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_get_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_get_code:
                if ("".equals(etAccount.getText().toString()) || !CheckUtils.isPhone(etAccount.getText().toString())) {
                    ToastUtils.show(mContext, "手机号不正确！", ToastUtils.LENGTH_LONG);
                    return;
                }
                count();
                mPresenter.getCode(this);
                break;
            case R.id.tv_confirm:
                String phone = etAccount.getText().toString();
                String pass = etPass.getText().toString();
                String code = etCode.getText().toString();
                String passtwo = etPassAgain.getText().toString();
                if (!CheckUtils.isPhone(phone)) {
                    ToastUtils.show(mContext, "手机号不正确！", ToastUtils.LENGTH_LONG);
                    return;
                }
                if (pass.length() < 8 || pass.length() > 16) {
                    ToastUtils.show(mContext, "密码长度不能低于8位！", ToastUtils.LENGTH_LONG);
                    return;
                }
                if (!pass.equals(passtwo)) {
                    ToastUtils.show(mContext, "两次密码输入不一致", ToastUtils.LENGTH_LONG);
                    return;
                }
                mPresenter.updatepwd(this);
                break;
        }
    }

    public void getEditText() {
        String phone = etAccount.getText().toString();
        String code = etCode.getText().toString();
        String pass = etPass.getText().toString();
        String passtwo = etPassAgain.getText().toString();
        if ("".equals(phone) || "".equals(pass) || "".equals(code) || "".equals(passtwo)) {
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

    private void count() {
        new Thread() {
            @Override
            public void run() {
                tvGetCode.setClickable(false);
                for (int i = 0; i < 60; i++) {
                    if (isStop) {
                        break;
                    }
                    if (!isbreak) {
                        Message message = handler.obtainMessage();
                        message.what = 0x01;
                        message.arg1 = i;
                        handler.sendMessage(message);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == 59) {
                        if (!isbreak) {
                            Message messages = handler.obtainMessage();
                            messages.what = 0x02;
                            handler.sendMessage(messages);
                        }
                    }
                }
            }
        }.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        isbreak = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true;
    }


    @Override
    public UpdatePWD getData() {
        String phone = etAccount.getText().toString();
        String pass = etPass.getText().toString();
        String code = etCode.getText().toString();
        UpdatePWD data = new UpdatePWD();
        data.setCode(code);
        data.setPhone(phone);
        data.setPassword(pass);
        return data;
    }

    @Override
    public String getPhone() {
        return etAccount.getText().toString();
    }

    @Override
    public void onRequestSuccessData(String data) {
        if ("1000".equals(data)) {
            ToastUtils.show(mContext, "修改成功", ToastUtils.LENGTH_LONG);
            finish();
        }
    }
}
