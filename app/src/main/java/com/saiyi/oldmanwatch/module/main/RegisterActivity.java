package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.RequestRegister;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.mvp.presenter.RegisterPresenter;
import com.saiyi.oldmanwatch.mvp.view.RegisterView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/20
 * @Describe 注册页面
 */
public class RegisterActivity extends BaseMvpAppCompatActivity<RegisterPresenter> implements RegisterView<BaseResponse> {


    @BindView(R.id.tv_Left)
    TextView tvLeft;
    @BindView(R.id.tv_error_code)
    TextView tvErrorCode;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.img_account_del)
    ImageView ivAccountDel;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.img_see_pass)
    ImageView ivSeePass;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_hint_account)
    TextView tvHintAccount;

    private Context mContext;
    private boolean isStop;
    private boolean isbreak;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x01) {
                tvGetCode.setText((60 - msg.arg1) + "(S)");
                tvGetCode.setTextColor(getResources().getColor(R.color.hint_color));
            } else if (msg.what == 0x02) {
                tvGetCode.setText("获取验证码");
                tvGetCode.setTextColor(getResources().getColor(R.color.appColor));
                tvGetCode.setClickable(true);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = RegisterActivity.this;
        tvConfirm.setClickable(false);
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getEditText();
                if (s.length() == 11)
                    mPresenter.queryPhone(RegisterActivity.this);
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
            public void afterTextChanged(Editable s) {
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
            public void afterTextChanged(Editable s) {
                getEditText();
            }
        });
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initData() {
        CheckUtils.setEditTextInhibitInputSpace(etPass);
    }


    @OnClick({R.id.tv_Left, R.id.img_account_del, R.id.tv_get_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_Left:
                finish();
                break;
            case R.id.img_account_del:
                getEditText();
                etAccount.setText("");
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
                if (!CheckUtils.isPhone(phone)) {
                    ToastUtils.show(mContext, "手机号不正确！", ToastUtils.LENGTH_LONG);
                    return;
                }
                if (pass.length() < 8 || pass.length() > 16) {
                    ToastUtils.show(mContext, "密码长度不能低于8位！", ToastUtils.LENGTH_LONG);
                    return;
                }

                mPresenter.register(this);
                break;
        }
    }

    public void getEditText() {
        String phone = etAccount.getText().toString();
        String pass = etPass.getText().toString();
        String code = etCode.getText().toString();
        if ("".equals(phone) || "".equals(pass) || "".equals(code)) {
            tvConfirm.setBackgroundResource(R.drawable.btn_gray_shape);
            tvConfirm.setClickable(false);
        } else {
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
    public RequestRegister getData() {
        RequestRegister register = new RequestRegister();
        register.setPhone(etAccount.getText().toString());
        register.setPassword(etPass.getText().toString());
        register.setCode(etCode.getText().toString());
        return register;
    }

    @Override
    public String getPhone() {
        return etAccount.getText().toString();
    }

    @Override
    public void queryPhone(String code) {
        switch (code) {
            case "3001":
                tvHintAccount.setVisibility(View.INVISIBLE);
                tvConfirm.setClickable(true);
                break;
            case "3002":
                tvHintAccount.setVisibility(View.VISIBLE);
                tvConfirm.setClickable(false);
                break;
        }
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
    public void onRequestSuccessData(BaseResponse data) {
        switch (data.getCode()) {
            case "1000":
                ToastUtils.show(mContext, "注册成功！", ToastUtils.LENGTH_LONG);
                tvHintAccount.setVisibility(View.INVISIBLE);
                finish();
                break;
            case "1002":
                ToastUtils.show(mContext, "注册失败！", ToastUtils.LENGTH_LONG);
                tvHintAccount.setVisibility(View.INVISIBLE);
                break;
            case "2002":
                ToastUtils.show(mContext, "验证码过期！", ToastUtils.LENGTH_LONG);
                tvHintAccount.setVisibility(View.INVISIBLE);
                tvErrorCode.setVisibility(View.VISIBLE);
                break;
            case "3001":
                ToastUtils.show(mContext, "验证码错误！", ToastUtils.LENGTH_LONG);
                tvHintAccount.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }
}
