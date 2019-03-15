package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.OnlyDeviceBean;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/3/1.
 */
public class OnlyDeviceAdapter extends AbsBaseAdapter<OnlyDeviceBean.DataBean,OnlyDeviceAdapter.OnlyViewHolder> {


    public OnlyDeviceAdapter(Context context ) {
        super(context, R.layout.layout_my_item );
    }

    @Override
    public OnlyViewHolder onCreateVH(View itemView, int ViewType) {
        return new OnlyViewHolder(itemView);
    }


    @Override
    public void setListData(List<OnlyDeviceBean.DataBean> listData) {
        super.setListData(listData);

        Log.e("http   listData",listData.size()+"");
    }

    @Override
    public void onBindDataForItem(OnlyViewHolder viewHolder, OnlyDeviceBean.DataBean bean, int position, List<OnlyDeviceBean.DataBean> mData) {


        viewHolder.iv_icon.setImageResource(R.mipmap.equipment_scale2);
        viewHolder.tv_title.setText("体脂秤");

    }

    public class OnlyViewHolder extends  BaseViewHolder{

        ImageView iv_icon;
        TextView  tv_title;

        public OnlyViewHolder(View itemView) {
            super(itemView);

            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);

        }
    }
}
