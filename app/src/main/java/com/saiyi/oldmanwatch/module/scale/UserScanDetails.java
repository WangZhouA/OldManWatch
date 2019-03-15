package com.saiyi.oldmanwatch.module.scale;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.view.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 最帅的男人 on 2019/2/28.
 */
public class UserScanDetails extends BaseMvpAppCompatActivity {


    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.user_pic)
    CircleImageView userPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.imNameB)
    ImageView imNameB;
    @BindView(R.id.tvHight)
    TextView tvHight;
    @BindView(R.id.imHightB)
    ImageView imHightB;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.imSexB)
    ImageView imSexB;
    @BindView(R.id.tvBirthday)
    TextView tvBirthday;
    @BindView(R.id.imBirthdayB)
    ImageView imBirthdayB;


    String  name ;
    String sex;
    String birthday;
    String height;
    String headUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_scan;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarLeft.setBackgroundResource(R.mipmap.back);

    }

    @Override
    protected void initData() {


        Intent intent = getIntent();
         name = intent.getStringExtra("name");
         sex=  intent.getStringExtra("sex");
         birthday= intent.getStringExtra("birthday");
         height=intent.getStringExtra("height");
         headUrl= intent.getStringExtra("headUrl");

        if (TextUtils.isEmpty(name) || name.equals("null")){

            tvName.setText("");
        }else {
            tvBarTitle.setText(name);
            tvName.setText(name);
        }
        if (TextUtils.isEmpty(sex)|| sex.equals("null")){

            tvSex.setText("");
        }else {
            tvSex.setText(sex);
        }
        if (TextUtils.isEmpty(birthday)|| birthday.equals("null")){

            tvBirthday.setText("");
        }else {
            tvBirthday.setText(birthday);
        }
        if (TextUtils.isEmpty(height)|| height.equals("null")){

            tvHight.setText("");
        }else {
            tvHight.setText(height);
        }
//        if (TextUtils.isEmpty(headUrl)){
//
//        }else {
//
//        }


    }



    @OnClick(R.id.tv_BarLeft)
    public void onViewClicked() {

        finish();
    }
}
