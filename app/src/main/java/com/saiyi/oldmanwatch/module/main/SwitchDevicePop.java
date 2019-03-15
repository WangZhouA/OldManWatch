package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.MultiItemTypeAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/9
 * @Describe
 */
public class SwitchDevicePop extends PopupWindow {
    private Context mContext;
    private View mMenuView;
    private RecyclerView mRecyclerView;
    private LayoutInflater inflater = null;
    private List<QueryDeviceList> mDatas;
    private View view;
    private itemSelectListener mItemSelectListener;
    CommonAdapter<QueryDeviceList> mAdapter;

    public SwitchDevicePop(Context mContext, List<QueryDeviceList> mDatas, View view) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.view = view;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_switch_device, null);
        initWidget();
        setPopup();
        setData();
    }


    /**
     * 初始化控件
     */
    private void initWidget() {
        mRecyclerView = mMenuView.findViewById(R.id.rv_retrieval);

    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(mMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(view.getWidth() * 3);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(view.getHeight() * 4);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为白色
        ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.appWhite));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

    private void setData() {
        if (mDatas == null)
            return;
        mAdapter = new CommonAdapter<QueryDeviceList>(mContext, R.layout.item_switch_device, mDatas) {
            @Override
            protected void convert(ViewHolder holder, QueryDeviceList dataBean, int position) {
                ImageView img = holder.getView(R.id.civ_pic);
                holder.setText(R.id.tv_name, dataBean.getFiliation());
                Glide.with(mContext).load(dataBean.getHeadUrl() == null ? "" : dataBean.getHeadUrl()).error(Util.getRes(dataBean.getFiliation())).into(img);

            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                mItemSelectListener.getDevice(mDatas.get(position));
                dismiss();
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


    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void show(View view) {

        showAsDropDown(view);
    }

    public interface itemSelectListener {
        void getDevice(QueryDeviceList info);
    }

    public void setItemSelectListener(itemSelectListener itemSelectListener) {
        this.mItemSelectListener = itemSelectListener;
    }
}
