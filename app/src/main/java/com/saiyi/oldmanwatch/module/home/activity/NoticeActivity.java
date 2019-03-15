package com.saiyi.oldmanwatch.module.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.NoticeList;
import com.saiyi.oldmanwatch.mvp.presenter.NoticePresenter;
import com.saiyi.oldmanwatch.mvp.view.NoticeView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.SwipeItemLayout;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/28
 * @Describe 消息通知
 */
public class NoticeActivity extends BaseMvpAppCompatActivity<NoticePresenter> implements NoticeView<List<NoticeList>> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.rv_notice)
    LRecyclerView mRecyclerView;

    private Context mContext;
    private String mac;
    private String token;
    private CommonAdapter<NoticeList> mAdapter;
    private List<NoticeList> mDatas;
    private int Nid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.notice);
        mContext = this;
    }

    @Override
    protected NoticePresenter createPresenter() {
        return new NoticePresenter(this);
    }

    @Override
    protected void initData() {
        mac = CheckUtils.getMac();
        token = (String) SharedPreferencesHelper.get("token", "");
        mPresenter.getNoticeList(this);
    }

    @OnClick({R.id.tv_BarLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
        }
    }


    @Override
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getNid() {
        return Nid;
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
    public void onRequestSuccessData(List<NoticeList> data) {
        mDatas = new ArrayList<>();
        mDatas.addAll(data);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mAdapter = new CommonAdapter<NoticeList>(mContext, R.layout.item_notice, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final NoticeList dataBean, int position) {
                ImageView ivPic = holder.getView(R.id.iv_notice);
                holder.setText(R.id.tv_title, dataBean.getTitle());
                holder.setText(R.id.tv_time, dataBean.getDate());
                Glide.with(mContext).load(R.mipmap.personal_notice).into(ivPic);
                holder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Nid = dataBean.getId();
                        mPresenter.delNotice(NoticeActivity.this);
                    }
                });
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }
}
