package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.UserMangerMacBean;
import com.saiyi.oldmanwatch.listener.MyOnSetItemListeners;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/13.
 */
public class UserMangerAdapter extends AbsBaseAdapter<UserMangerMacBean.DataBean,UserMangerAdapter.MyViewHolder> {

    private Context mContext;


    MyOnSetItemListeners myOnSetItemListeners;


    public void setMyOnSetItemListeners(MyOnSetItemListeners myOnSetItemListeners) {
        this.myOnSetItemListeners = myOnSetItemListeners;
    }

    public UserMangerAdapter(Context context) {
        super(context, R.layout.item_user_mangement);
        mContext=context;
    }



    @Override
    public MyViewHolder onCreateVH(View itemView, int ViewType) {
        return new MyViewHolder(itemView);
    }


    @Override
    public UserMangerMacBean.DataBean getItemBean(int position) {
        return super.getItemBean(position);
    }




    @Override
    public void setListData(List<UserMangerMacBean.DataBean> listData) {
        super.setListData(listData);
    }


    @Override
    protected void setItemListeners(MyViewHolder holder, UserMangerMacBean.DataBean dataBean, int position) {
        super.setItemListeners(holder, dataBean, position);

        holder.lin_user_manger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnSetItemListeners.MyOnSetItemListeners(dataBean,position);
            }
        });
    }

    @Override
    public void onBindDataForItem(MyViewHolder viewHolder, UserMangerMacBean.DataBean bean, int position, List<UserMangerMacBean.DataBean> mData) {

        viewHolder.name.setText(bean.getName());
        if (bean.getType().equals("1")){
            viewHolder.rl.setBackgroundResource(R.drawable.copy_btn_yellow_shape);
            viewHolder.type.setText("管理员");
        }else {
            viewHolder.rl.setBackgroundResource(R.drawable.copy_btn_blue_shape);
            viewHolder.type.setText("成员");
        }

        viewHolder.number.setText(bean.getPhone());


    }

    public class MyViewHolder extends BaseViewHolder {

        TextView name;
        TextView type;
        TextView number;
        RelativeLayout rl;
        LinearLayout lin_user_manger;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            type = itemView.findViewById(R.id.tv_type);
            number = itemView.findViewById(R.id.tv_number);
            rl = itemView.findViewById(R.id.rl);
            lin_user_manger = itemView.findViewById(R.id.lin_user_manger);


        }
    }
}
