package com.saiyi.oldmanwatch.module.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.entity.UserBean;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.module.health.activity.HealthReportActivity;
import com.saiyi.oldmanwatch.module.home.activity.NoticeActivity;
import com.saiyi.oldmanwatch.module.main.DeviceTypeActivity;
import com.saiyi.oldmanwatch.module.main.LoginActivity;
import com.saiyi.oldmanwatch.module.user.activity.AboutUsActivity;
import com.saiyi.oldmanwatch.module.user.activity.FeedbackActivity;
import com.saiyi.oldmanwatch.module.user.activity.MyPositionActivity;
import com.saiyi.oldmanwatch.module.user.activity.MyWatchActivity;
import com.saiyi.oldmanwatch.module.user.activity.PerfectPersonalAvtivity;
import com.saiyi.oldmanwatch.module.user.activity.ReSetPwdActivity;
import com.saiyi.oldmanwatch.mvp.presenter.MePresenter;
import com.saiyi.oldmanwatch.mvp.view.MeView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.CircleImageView;
import com.saiyi.oldmanwatch.view.MyItemView;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.MultiItemTypeAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/28
 * @Describe
 */
public class MeFragment extends BaseMvpFragment<MePresenter> implements MeView<UserBean> {

    @BindView(R.id.rl_my_bg)
    RelativeLayout rlMyBg;
    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rv_device)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_add_device)
    LinearLayout llAddDevice;
    @BindView(R.id.miv_report)
    MyItemView mivReport;
    @BindView(R.id.miv_reset_pwd)
    MyItemView mivReSetPwd;
    @BindView(R.id.miv_feedback)
    MyItemView mivFeedback;
    @BindView(R.id.miv_about_us)
    MyItemView mivAboutUs;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;

    private Context mContext;
    private String name;
    private String token;
    private int uid;
    private String mac;
    private CommonAdapter<QueryDeviceList> mAdapter;
    private List<QueryDeviceList> mDatas = new ArrayList<>();
    private String type;
    private String age;
    private String headUrl;
    private String phone;
    private String sex;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        initView();
        initData();
    }


    private void initView() {
        mivReport.setIconRes(R.mipmap.personal_report);
        mivReport.setText(getResources().getString(R.string.health_report));
        mivReport.setRightRes(R.mipmap.into);
        mivReSetPwd.setIconRes(R.mipmap.personal_set);
        mivReSetPwd.setText(getResources().getString(R.string.reset_pwd));
        mivReSetPwd.setRightRes(R.mipmap.into);
        mivFeedback.setIconRes(R.mipmap.personal_feedback);
        mivFeedback.setText(getResources().getString(R.string.feedback));
        mivFeedback.setRightRes(R.mipmap.into);
        mivAboutUs.setIconRes(R.mipmap.personal_about);
        mivAboutUs.setText(getResources().getString(R.string.about_us));
        mivAboutUs.setRightRes(R.mipmap.into);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryDeviceList(this);
        mPresenter.getUser(this);
    }


    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    private void initData() {
        token = (String) SharedPreferencesHelper.get("token", "");
        uid = (int) SharedPreferencesHelper.get("uid", -1);
        type = (String) SharedPreferencesHelper.get("type", "");
        mac = CheckUtils.getMac();
        if ("2".equals(type)) {
            mivReport.setIconRes(R.mipmap.personal_notice);
            mivReport.setText(getResources().getString(R.string.msg_notice));
            mivReport.setRightRes(R.mipmap.into);
            rlMyBg.setBackgroundResource(R.mipmap.bg3);
        } else {
            rlMyBg.setBackgroundResource(R.mipmap.bg);
        }
    }

    @OnClick({R.id.civ_pic, R.id.miv_report, R.id.ll_add_device, R.id.miv_reset_pwd, R.id.miv_feedback, R.id.miv_about_us, R.id.tv_login_out})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.civ_pic:
                intent.setClass(mContext, PerfectPersonalAvtivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("sex", sex);
                intent.putExtra("phone", phone);
                intent.putExtra("headUrl", headUrl);
                mContext.startActivity(intent);
                break;
            case R.id.ll_add_device:
                intent.setClass(mContext, DeviceTypeActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.miv_report:
                if ("2".equals(type))
                    intent.setClass(mContext, NoticeActivity.class);
                else
                    intent.setClass(mContext, HealthReportActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.miv_reset_pwd:
                intent.setClass(mContext, ReSetPwdActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.miv_feedback:
                intent.setClass(mContext, FeedbackActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.miv_about_us:
                intent.setClass(mContext, AboutUsActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.tv_login_out:
                SharedPreferencesHelper.clear();
                intent.setClass(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                getActivity().finish();
                break;
        }
    }


    @Override
    public void getData(List<QueryDeviceList> data) {
        mDatas.clear();
        mDatas.addAll(data);
        mAdapter = new CommonAdapter<QueryDeviceList>(mContext, R.layout.layout_my_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, QueryDeviceList dataBean, int position) {
                ImageView icon = holder.getView(R.id.iv_icon);
                ImageView right = holder.getView(R.id.iv_right);
//                holder.setText(R.id.tv_title, ("1".equals(dataBean.getType())) ? "智能手表" : "定位器");
                if ("1".equals(dataBean.getType()))
                    holder.setText(R.id.tv_title, "智能手表");
                else holder.setText(R.id.tv_title, "定位器");
                if (!TextUtils.isEmpty(dataBean.getFiliation())) {
                    Glide.with(mContext).load(dataBean.getHeadUrl()).error(Util.getRes(dataBean.getFiliation())).into(icon);
                }
                right.setImageResource(R.mipmap.into);
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                Intent intent = new Intent();

                if ("1".equals(mDatas.get(position).getType())) {
                    intent.setClass(mContext, MyWatchActivity.class);
                    intent.putExtra("type", "1");

                } else {
                    intent.setClass(mContext, MyPositionActivity.class);
                    intent.putExtra("type", "2");
                }
                intent.putExtra("headUrl", mDatas.get(position).getHeadUrl());
                intent.putExtra("filiation", mDatas.get(position).getFiliation());
                intent.putExtra("mac", mDatas.get(position).getMac());
                BaseApplication.mac = mDatas.get(position).getMac() == null ? "" : mDatas.get(position).getMac();
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public String getToken() {
        return token;
    }


    @Override
    public void onRequestSuccessData(UserBean data) {




        Logger.e("ssss", "data......" + data.toString());
        tvName.setText(data.getName() == null ? "" : data.getName());
        name = data.getName() == null ? "" : data.getName();
        age = data.getAge() == null ? "" : data.getAge();
        headUrl = data.getHeadUrl() == null ? "" : data.getHeadUrl();
        phone = data.getPhone() == null ? "" : data.getPhone();
        sex = data.getSex() == null ? "" : data.getSex();
        Glide.with(mContext).load(Constant.getRoot() + data.getHeadUrl()).error(R.mipmap.head_portrait).into(civPic);
    }
}
