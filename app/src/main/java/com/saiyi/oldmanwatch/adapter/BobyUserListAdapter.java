package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.http.Constant;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/23.
 */
public class BobyUserListAdapter extends AbsBaseAdapter<bodyUserListBean,BobyUserListAdapter.bobyUserLisyMyViewHolder> {


    Context mContext;
    DialogOnsetListener dialogOnsetListener;

    public void setDialogOnsetListener(DialogOnsetListener dialogOnsetListener) {
        this.dialogOnsetListener = dialogOnsetListener;
    }

    public BobyUserListAdapter(Context context ) {
        super(context, R.layout.item_select_use_name);
        this.mContext=context;

    }
    public BobyUserListAdapter(Context context,int layout) {
        super(context, layout);
        this.mContext=context;
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
        Glide.with(mContext).load(Constant.getRoot() + bean.getHeadUrl()).error(R.mipmap.call2).into(viewHolder.imPic);



    }

    public class  bobyUserLisyMyViewHolder extends  BaseViewHolder{

        TextView tvName;
        ImageView imPic;
        ConstraintLayout cl;

        public bobyUserLisyMyViewHolder(View itemView) {
            super(itemView);
            tvName =itemView.findViewById(R.id.tv_name);
            imPic =itemView.findViewById(R.id.iv_pic);
            cl =itemView.findViewById(R.id.cl);
        }
    }


    public interface  DialogOnsetListener{
        void  setDialogOnsetListener(int id,String name);
    }


}
