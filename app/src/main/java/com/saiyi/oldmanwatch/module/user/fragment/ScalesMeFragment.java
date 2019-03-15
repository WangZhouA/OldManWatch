package com.saiyi.oldmanwatch.module.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.saiyi.oldmanwatch.module.main.DeviceTypeActivity;
import com.saiyi.oldmanwatch.module.main.LoginActivity;
import com.saiyi.oldmanwatch.module.scale.ScanInfoActivity;
import com.saiyi.oldmanwatch.module.user.activity.FeedbackActivity;
import com.saiyi.oldmanwatch.module.user.activity.MyPositionActivity;
import com.saiyi.oldmanwatch.module.user.activity.MyWatchActivity;
import com.saiyi.oldmanwatch.module.user.activity.PerfectPersonalAvtivity;
import com.saiyi.oldmanwatch.module.user.activity.ReSetPwdActivity;
import com.saiyi.oldmanwatch.mvp.presenter.MePresenter;
import com.saiyi.oldmanwatch.mvp.view.MeView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
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
 * 体脂称个人中心
 */
public class ScalesMeFragment extends BaseMvpFragment<MePresenter> implements MeView<UserBean> {
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
    @BindView(R.id.miv_reset_password)
    MyItemView mivReSetPwd;
    @BindView(R.id.miv_feedback)
    MyItemView mivFeedback;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;
    @BindView(R.id.iv_bianji)
    ImageView mIvBianji;

    private Context mContext;
    private String name;
    private String birthday;
    private String token;
    private int uid;
    private String mac;
    private CommonAdapter<QueryDeviceList> mAdapter;
    private List<QueryDeviceList> mDatas = new ArrayList<>();
    private List<QueryDeviceList> ScalesDatas = new ArrayList<>();
    private String type;
    private String age;
    private String headUrl;
    private String phone;
    private String sex;
    private String height;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_scales_me;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        mivReSetPwd.setIconRes(R.mipmap.personal_set);
        mivReSetPwd.setText(getResources().getString(R.string.reset_pwd));
        mivReSetPwd.setRightRes(R.mipmap.into);
        mivFeedback.setIconRes(R.mipmap.personal_feedback);
        mivFeedback.setText(getResources().getString(R.string.feedback));
        mivFeedback.setRightRes(R.mipmap.into);
        token = (String) SharedPreferencesHelper.get("token", "");
        uid = (int) SharedPreferencesHelper.get("uid", -1);
        type = (String) SharedPreferencesHelper.get("type", "");
        mac = CheckUtils.getMac();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));


    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.queryDeviceList(this); //这个接口是查询全部设备的接口
        mPresenter.getUser(this);

//        okHttpQueryScales();         //这个接口只查询 体脂秤的接口


    }



    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @OnClick({R.id.iv_bianji, R.id.ll_add_device, R.id.miv_reset_password, R.id.miv_feedback, R.id.tv_login_out})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_bianji:
                intent.setClass(mContext, PerfectPersonalAvtivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("sex", sex);
                intent.putExtra("phone", phone);
                intent.putExtra("headUrl", headUrl);
                intent.putExtra("birthday", birthday);
                intent.putExtra("height", height);
                mContext.startActivity(intent);
                break;
            case R.id.ll_add_device:
//                intent.setClass(mContext, DeviceTypeActivity.class);
//                mContext.startActivity(intent);
                intent.setClass(mContext, DeviceTypeActivity.class);
                startActivity(intent);
                break;
            case R.id.miv_reset_password:
                intent.setClass(mContext, ReSetPwdActivity.class);
                mContext.startActivity(intent);

                break;
            case R.id.miv_feedback:
                intent.setClass(mContext, FeedbackActivity.class);
                mContext.startActivity(intent);
//                intent.setClass(mContext, MainTitleActivity.class);
//                mContext.startActivity(intent);
//                             intent.setClass(mContext, JiGuangActivity.class);
//                mContext.startActivity(intent);




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
        for (int i= 0;i<data.size();i++){

            if (data.get(i).getType().equals("3")){

                mDatas.add(data.get(i));
            }

        }

        mAdapter = new CommonAdapter<QueryDeviceList>(mContext, R.layout.layout_my_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, QueryDeviceList dataBean, int position) {
                ImageView icon = holder.getView(R.id.iv_icon);
                ImageView right = holder.getView(R.id.iv_right);
                ImageView iv_icon =holder.getView(R.id.iv_icon);
//                holder.setText(R.id.tv_title, ("1".equals(dataBean.getType())) ? "智能手表" : "定位器");
                if ("1".equals(dataBean.getType())) {
                    holder.setText(R.id.tv_title, "智能手表");
                    iv_icon.setImageResource(R.mipmap.equipment_watch2);
                }else if ("2".equals(dataBean.getType())){
                    holder.setText(R.id.tv_title, "定位器");
                    iv_icon.setImageResource(R.mipmap.equipment_locator2);
                }else {
                    holder.setText(R.id.tv_title, "体脂秤");
                    iv_icon.setImageResource(R.mipmap.equipment_scale2);
                }

//                if (TextUtils.isEmpty(dataBean.getFiliation())){
//
//                }else {
//                    Glide.with(mContext).load(dataBean.getHeadUrl()).error(Util.getRes(dataBean.getFiliation())).into(icon);
//                }
                right.setImageResource(R.mipmap.into);
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {

                /**
                 *   先屏蔽
                 * */

                Intent intent = new Intent();


//
                if ("1".equals(mDatas.get(position).getType())) {
                    intent.setClass(mContext, MyWatchActivity.class);
                    intent.putExtra("type", "1");
                } else if ("2".equals(mDatas.get(position).getType())){
                    intent.setClass(mContext, MyPositionActivity.class);
                    intent.putExtra("type", "2");
                }else {
                    intent.setClass(mContext, ScanInfoActivity.class);
                    intent.putExtra("type", "3");
                }
//                intent.putExtra("headUrl", mDatas.get(position).getHeadUrl());
//                intent.putExtra("filiation", mDatas.get(position).getFiliation());
                intent.putExtra("mac", mDatas.get(position).getMac());
                BaseApplication.mac = mDatas.get(position).getMac() == null ? "" : mDatas.get(position).getMac();
                startActivity(intent);

//
//                intent.setClass(mContext, ScanInfoActivity.class);
//                intent.putExtra(RetrofitService.MAC,mDatas.get(position).getMac());
//                Log.e("----->mac地址是",mDatas.get(position).getMac());
//                mContext.startActivity(intent);


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
        tvName.setText(data.getName() == null ? "" : data.getName());
        name = data.getName() == null ? "" : data.getName();
        age = data.getAge() == null ? "" : data.getAge();
        headUrl = data.getHeadUrl() == null ? "" : data.getHeadUrl();
        phone = data.getPhone() == null ? "" : data.getPhone();
        sex = data.getSex() == null ? "" : data.getSex();
        birthday= data.getBirthday()==null?"":data.getBirthday();
        height =data.getHeight()==null?"":data.getHeight();
        Glide.with(mContext).load(Constant.getRoot() + data.getHeadUrl()).error(R.mipmap.head_portrait).into(civPic);
    }


}
