package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.QueryRemindList;
import com.saiyi.oldmanwatch.entity.ReimndList;
import com.saiyi.oldmanwatch.mvp.presenter.RemindListPresenter;
import com.saiyi.oldmanwatch.mvp.view.RemindListView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.SwipeItemLayout;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/26
 * @Describe 用药提醒
 */
public class DrugRemindingActivity extends BaseMvpAppCompatActivity<RemindListPresenter> implements RemindListView<List<ReimndList>> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.rv_drug)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_hint_drug)
    TextView tvHintDrug;
    @BindView(R.id.ll_add_reminding)
    LinearLayout tvAddReminding;


    private Context mContext;
    private String mac;
    private String token;
    private CommonAdapter<ReimndList> mAdapter;
    private List<ReimndList> mDatas = new ArrayList<>();
    private int rid = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drug_reminding;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(getResources().getString(R.string.drug_remind));
    }

    @Override
    protected RemindListPresenter createPresenter() {
        return new RemindListPresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
        token = (String) SharedPreferencesHelper.get("token", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryData();
    }

    private void queryData() {
        mPresenter.getRemindList(this);
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public int getRid() {
        return rid;
    }

    @OnClick({R.id.tv_BarLeft, R.id.ll_add_reminding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.ll_add_reminding:
                Intent intent = new Intent();
                intent.setClass(mContext, CreateDrugRemindingActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "删除成功！", ToastUtils.LENGTH_LONG);
        }
        queryData();
    }

    @Override
    public QueryRemindList getData() {
        QueryRemindList data = new QueryRemindList();
        data.setMac(mac);
        data.setType("2");
        return data;
    }

    public String getCycle(String str) {
        String cycle = "";
        switch (str) {
            case "1":
                cycle = "一次";
                break;
            case "2":
                cycle = "每天";
                break;
            case "3":
                cycle = "周一至周五";
                break;
            case "4":
                break;
        }
        return cycle;
    }

    @Override
    public void onRequestSuccessData(List<ReimndList> data) {
        mDatas.clear();
        if (data != null) {
            tvHintDrug.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mDatas.addAll(data);
        } else {
            tvHintDrug.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        if (mAdapter == null) {
            mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, 1));
            mAdapter = new CommonAdapter<ReimndList>(mContext, R.layout.item_drug_remind, mDatas) {
                @Override
                protected void convert(ViewHolder holder, final ReimndList dataBean, int position) {
                    holder.setText(R.id.tv_title, dataBean.getTitle());
                    holder.setText(R.id.tv_cycle, (!"".equals(getCycle(dataBean.getCycle())) ? getCycle(dataBean.getCycle()) : dataBean.getPeriod()));
                    holder.setText(R.id.tv_time, dataBean.getRemindTime());
                    holder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rid = dataBean.getId();
                            mPresenter.delRemind(DrugRemindingActivity.this);
                        }
                    });
                }
            };
            mRecyclerView.setAdapter(mAdapter);
        } else mAdapter.notifyDataSetChanged();
    }
}
