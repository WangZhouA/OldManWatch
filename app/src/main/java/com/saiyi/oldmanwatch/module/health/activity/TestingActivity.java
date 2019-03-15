package com.saiyi.oldmanwatch.module.health.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.manager.LocalBroadcastManager;
import com.saiyi.oldmanwatch.module.map.fragment.MapFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/10
 * @Describe
 */
public class TestingActivity extends BaseAppCompatActivity {

    @BindView(R.id.tv_confirm)
    TextView tvConfirm;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_testing;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        registerMessageReceiver();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                break;
        }
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.HEART_MESSAGE_RECEIVED_ACTION";
    public static final String V_HIGH_PRESSURE = "high";
    public static final String V_LOW_PRESSURE = "low";
    public static final String V_HEART_RATE = "heart";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    /**
     * 广播
     */
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String high = intent.getStringExtra(V_HIGH_PRESSURE);
                    String low = intent.getStringExtra(V_LOW_PRESSURE);
                    String heart = intent.getStringExtra(V_HEART_RATE);
                    Intent mInt = new Intent();
                    mInt.putExtra("high", high);
                    mInt.putExtra("low", low);
                    mInt.putExtra("heart", heart);
                    TestingActivity.this.setResult(Constant.TESTCODE, mInt);
                    finish();
                }
            } catch (Exception e) {

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}
