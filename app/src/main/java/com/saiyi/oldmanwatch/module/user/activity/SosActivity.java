package com.saiyi.oldmanwatch.module.user.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.AddSosBean;
import com.saiyi.oldmanwatch.entity.SosList;
import com.saiyi.oldmanwatch.entity.SosPhoneBean;
import com.saiyi.oldmanwatch.module.home.activity.TelephoneBookActivity;
import com.saiyi.oldmanwatch.mvp.presenter.SosPresenter;
import com.saiyi.oldmanwatch.mvp.view.SosView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class SosActivity extends BaseMvpAppCompatActivity<SosPresenter> implements SosView<List<SosPhoneBean>> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.tv_num1)
    TextView tvNum1;
    @BindView(R.id.tv_num2)
    TextView tvNum2;
    @BindView(R.id.tv_num3)
    TextView tvNum3;

    private Context mContext;
    private String mac;
    private String token;
    private int SELECT = 0;
    private String type;


    @Override
    protected int getLayoutId() {
        type = getIntent().getStringExtra("type");
        if ("2".equals(type))
            return R.layout.activity_sos_position;
        else
            return R.layout.activity_sos;

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.family_number);
        tvBarRight.setText(R.string.save);
    }

    @Override
    protected SosPresenter createPresenter() {
        return new SosPresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
        token = (String) SharedPreferencesHelper.get("token", "");
        mPresenter.getSosList(this);
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight, R.id.tv_num1, R.id.tv_num2, R.id.tv_num3})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                mPresenter.setSos(this);
                break;
            case R.id.tv_num1:
                getPermission();
                SELECT = 1;
                break;
            case R.id.tv_num2:
                getPermission();
                SELECT = 2;
                break;
            case R.id.tv_num3:
                getPermission();
                SELECT = 3;
                break;
        }
    }

    /**
     * 跳转联系人列表的回调函数
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data == null) {
                    return;
                }
                //处理返回的data,获取选择的联系人信息
                Uri uri = data.getData();
                String[] contacts = getPhoneContacts(uri);
                switch (SELECT) {
                    case 1:
                        tvNum1.setText(contacts[1]);
                        break;
                    case 2:
                        tvNum2.setText(contacts[1]);
                        break;
                    case 3:
                        tvNum3.setText(contacts[1]);
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getPermission() {
        //**版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取**
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //ContextCompat.checkSelfPermission() 方法 指定context和某个权限 返回PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                // 若不为GRANTED(即为DENIED)则要申请权限了
                // 申请权限 第一个为context 第二个可以指定多个请求的权限 第三个参数为请求码
                ActivityCompat.requestPermissions(SosActivity.this,
                        new String[]{android.Manifest.permission.READ_CONTACTS},
                        0);
            } else {
                //权限已经被授予，在这里直接写要执行的相应方法即可
                intentToContact();
            }
        } else {
            // 低于6.0的手机直接访问
            intentToContact();
        }
    }

    // 用户权限 申请 的回调方法
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intentToContact();
            } else {
                Toast.makeText(mContext, "授权被禁止", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    private void intentToContact() {
        // 跳转到联系人界面
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 0);
    }

    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        // 创建内容解析者
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = null;
        if (uri != null) {
            cursor = contentResolver.query(uri,
                    new String[]{"display_name", "data1"}, null, null, null);
        }
        String contactName = null;
        String phoneNum = null;
        while (cursor.moveToNext()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        cursor.close();
        //  把电话号码中的  -  符号 替换成空格
        if (phoneNum != null) {
            phoneNum = phoneNum.replaceAll("-", " ");
            // 空格去掉  为什么不直接-替换成"" 因为测试的时候发现还是会有空格 只能这么处理
            phoneNum = phoneNum.replaceAll(" ", "");
        }
        contact[0] = contactName;
        contact[1] = phoneNum;
        return contact;
    }

    @Override
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "设置成功", ToastUtils.LENGTH_LONG);
            finish();
        }
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public AddSosBean getData() {
        SosPhoneBean data = new SosPhoneBean();
        SosPhoneBean data1 = new SosPhoneBean();
        SosPhoneBean data2 = new SosPhoneBean();
        data.setFirst("1");
        data.setPhone(tvNum1.getText().toString());
        data1.setFirst("2");
        data1.setPhone(tvNum2.getText().toString());
        data2.setFirst("3");
        data2.setPhone(tvNum3.getText().toString());
        List<SosPhoneBean> list = new ArrayList<>();
        list.add(data);
        list.add(data1);
        list.add(data2);
        AddSosBean addSosBean = new AddSosBean();
        addSosBean.setContacts(list);
        addSosBean.setMac(mac);
        return addSosBean;
    }


    @Override
    public void onRequestSuccessData(List<SosPhoneBean> data) {
        List<SosPhoneBean> phones = new ArrayList<>();
        phones.addAll(data);
        for (int i = 0; i < phones.size(); i++) {
            if ("1".equals(phones.get(i).getFirst())) {
                tvNum1.setText(phones.get(i).getPhone());
            }
            if ("2".equals(phones.get(i).getFirst())) {
                tvNum2.setText(phones.get(i).getPhone());
            }
            if ("3".equals(phones.get(i).getFirst())) {
                tvNum3.setText(phones.get(i).getPhone());
            }
        }
    }
}
