package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.TrendListUserBean;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/27.
 */
public class TrendDateAdapter extends AbsBaseAdapter<TrendListUserBean.DataBean.ParametersBean,TrendDateAdapter.MyTrendDateViewHolder> {


    public TrendDateAdapter(Context context) {
        super(context, R.layout.item_list_date);
    }

    @Override
    public MyTrendDateViewHolder onCreateVH(View itemView, int ViewType) {
        return new MyTrendDateViewHolder(itemView);
    }


    @Override
    public void setListData(List<TrendListUserBean.DataBean.ParametersBean> listData) {
        super.setListData(listData);
    }

    @Override
    public void onBindDataForItem(MyTrendDateViewHolder viewHolder, TrendListUserBean.DataBean.ParametersBean bean, int position, List<TrendListUserBean.DataBean.ParametersBean> mData) {


        viewHolder.tvTime.setText(bean.getCreateDate());
        viewHolder.tvWeightNum.setText(bean.getWeight());
        viewHolder.tvPercentage.setText(bean.getBodyFatRate());

    }


    @Override
    protected void setItemListeners(MyTrendDateViewHolder holder, TrendListUserBean.DataBean.ParametersBean trendDateBean, int position) {
        super.setItemListeners(holder, trendDateBean, position);
    }

    public  class  MyTrendDateViewHolder extends BaseViewHolder{

        TextView tvTime;
        TextView tvWeightNum;
        TextView tvPercentage;


        ConstraintLayout ctl_date;

        public MyTrendDateViewHolder(View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tv_time);
            tvWeightNum = itemView.findViewById(R.id.tv_weight_num);
            tvPercentage = itemView.findViewById(R.id.tv_percentage);
            ctl_date = itemView.findViewById(R.id.ctl_date);

        }
    }
}
