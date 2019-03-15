package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.listener.OnSectorListener;
import com.saiyi.oldmanwatch.module.scale.JiGuangActivity;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/23.
 */
public class JiGuangUserListAdapter extends AbsBaseAdapter<bodyUserListBean,JiGuangUserListAdapter.bobyUserLisyMyViewHolder> implements OnSectorListener {


    Context mContext;

    private static  int myPostion=-1;

    OnSectorListener listener;

    public void setListener(OnSectorListener listener) {
        this.listener = listener;
    }

    public JiGuangUserListAdapter(Context context ) {
        super(context, R.layout.item_jg_user_scales);
        this.mContext=context;

        ((JiGuangActivity)mContext).setListener(this);

    }


    @Override
    public bobyUserLisyMyViewHolder onCreateVH(View itemView, int ViewType) {
        return new bobyUserLisyMyViewHolder(itemView);
    }

    @Override
    public void setListData(List<bodyUserListBean> listData) {
        super.setListData(listData);
    }


    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    protected void setItemListeners(bobyUserLisyMyViewHolder holder, bodyUserListBean bodyUserListBean, int position) {
        super.setItemListeners(holder, bodyUserListBean, position);




    }

    @Override
    public void onBindDataForItem(bobyUserLisyMyViewHolder viewHolder, bodyUserListBean bean, int position, List<bodyUserListBean> mData) {


        viewHolder.tvName.setText(bean.getName());
//        Glide.with(mContext).load(Constant.getRoot() + bean.getHeadUrl()).error(R.mipmap.call2).into(viewHolder.imPic);

        if (bean.isSelected()==true){

            viewHolder.iv_Sector.setImageResource(R.mipmap.select_voice);
        }else {
            viewHolder.iv_Sector.setImageResource(R.mipmap.unselect);
        }


        viewHolder.iv_Sector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实现单选，第一种方法，十分简单， Lv Rv通用,因为它们都有notifyDataSetChanged()方法
                // 每次点击时，先将所有的selected设为false，并且将当前点击的item 设为true， 刷新整个视图
                for (bodyUserListBean data : mData) {
                    data.setSelected(false);
                }
                mData.get(position).setSelected(true);
                myPostion = position;
                listener.setAdapterSectorListener();
                notifyDataSetChanged();


            }
        });

    }

    @Override
    public void setActivitySectorListener(List<bodyUserListBean> mData) {

        for (bodyUserListBean data : mData) {
            data.setSelected(false);
        }
        notifyDataSetChanged();

    }

    @Override
    public void setAdapterSectorListener() {

    }


    public class  bobyUserLisyMyViewHolder extends  BaseViewHolder{

        TextView tvName;
        ImageView imPic;
        ImageView  iv_Sector;
        ConstraintLayout cl;

        public bobyUserLisyMyViewHolder(View itemView) {
            super(itemView);
            tvName =itemView.findViewById(R.id.tvName);
            imPic =itemView.findViewById(R.id.imPic);
            cl =itemView.findViewById(R.id.cl);
            iv_Sector =itemView.findViewById(R.id.iv_Sector);
        }
    }


    public static  int  getPostion(){

        return  myPostion+1;
    }

}
