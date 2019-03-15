package com.saiyi.oldmanwatch.module.home.activity;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.ContactsBean;
import com.saiyi.oldmanwatch.entity.ContactsListBean;
import com.saiyi.oldmanwatch.mvp.presenter.TelephoneBookPresenter;
import com.saiyi.oldmanwatch.mvp.view.TelephoneBookView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.MultiItemTypeAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/25
 * @Describe
 */
public class TelephoneBookActivity extends BaseMvpAppCompatActivity<TelephoneBookPresenter> implements TelephoneBookView<List<ContactsListBean>> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.rv_phone_book)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context mContext;
    private CommonAdapter<ContactsBean.Contacts> mAdapter;
    private List<ContactsBean.Contacts> mContacts;
    private int SELECTID = -1;
    private String token;
    private String mac;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_telephone_book;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarTitle.setText(R.string.tel_book);
        tvBarleft.setBackgroundResource(R.mipmap.back);
    }

    @Override
    protected void initData() {
        mac = CheckUtils.getMac();
        token = (String) SharedPreferencesHelper.get("token", "");
        mPresenter.getContactsList(this);
    }

    @Override
    protected TelephoneBookPresenter createPresenter() {
        return new TelephoneBookPresenter(this);
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_confirm:
                ContactsBean data = new ContactsBean();
                data.setMac(mac);
                data.setContacts(mContacts);
                mPresenter.addContacts(this);
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
                mContacts.get(SELECTID).setPhone(contacts[1]);
                mContacts.get(SELECTID).setName(contacts[0]);
                mAdapter.notifyDataSetChanged();
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
                ActivityCompat.requestPermissions(TelephoneBookActivity.this,
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
        switch (code) {
            case "1000":
                ToastUtils.show(mContext, "操作成功!", ToastUtils.LENGTH_LONG);
                finish();
                break;
            case "1002":
                ToastUtils.show(mContext, "操作失败!", ToastUtils.LENGTH_LONG);
                break;
        }
    }

    @Override
    public ContactsBean getContacts() {
        for (int i = 0; i < mContacts.size(); i++) {
            mContacts.get(i).setFirst((i + 1) + "");
        }
        ContactsBean data = new ContactsBean();
        data.setMac(mac);
        data.setContacts(mContacts);
        return data;
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
    public void onRequestSuccessData(List<ContactsListBean> data) {
        mContacts = new ArrayList<>();
        if (null == data || data.size() == 0) {
            ContactsBean.Contacts tel = null;
            for (int i = 1; i < 11; i++) {
                tel = new ContactsBean().new Contacts();
                tel.setFirst("号码" + i);
                tel.setName("");
                tel.setPhone("");
                mContacts.add(tel);
            }
        } else {
            ContactsBean.Contacts tel = null;
            for (int i = 0; i < data.size(); i++) {
                tel = new ContactsBean().new Contacts();
                tel.setFirst("" + i);
                tel.setName(data.get(i).getName());
                tel.setPhone(data.get(i).getPhone());
                mContacts.add(tel);
            }
        }
        mAdapter = new CommonAdapter<ContactsBean.Contacts>(mContext, R.layout.item_telephone_book, mContacts) {
            @Override
            protected void convert(ViewHolder holder, ContactsBean.Contacts tel, int position) {
                holder.setText(R.id.tv_title, tel.getFirst());
                holder.setText(R.id.et_name, tel.getName());
                holder.setText(R.id.tv_phone_num, tel.getPhone());
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {

                getPermission();
                SELECTID = position;
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }
}
