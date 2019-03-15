package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.TrendListUserBean;
import com.saiyi.oldmanwatch.module.health.fragment.TrendDeviceDetails;
import com.saiyi.oldmanwatch.utils.TimeUtil;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/22.
 */
public class TrendAdapter extends AbsBaseAdapter<TrendListUserBean.DataBean.ParametersBean, TrendAdapter.MyViewHodler> {


    private Context mContext;

    public TrendAdapter(Context context) {
        super(context, R.layout.item_trend);
        mContext = context;

    }

    @Override
    public MyViewHodler onCreateVH(View itemView, int ViewType) {
        return new MyViewHodler(itemView);
    }


    @Override
    public void setListData(List<TrendListUserBean.DataBean.ParametersBean> listData) {
        super.setListData(listData);
    }


    @Override
    protected void setItemListeners(MyViewHodler holder, TrendListUserBean.DataBean.ParametersBean parametersBean, int position) {
        super.setItemListeners(holder, parametersBean, position);

        holder.ctl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, TrendDeviceDetails.class);
                intent.putExtra("weight",parametersBean.getWeight());
                intent.putExtra("score",parametersBean.getScore());
                intent.putExtra("size",parametersBean.getSize());
                intent.putExtra("bodyFatRate",parametersBean.getBodyFatRate());
                intent.putExtra("muscleRate",parametersBean.getMuscleRate());
                intent.putExtra("waterContent",parametersBean.getWaterContent());
                intent.putExtra("boneSalt",parametersBean.getBoneSalt());
                intent.putExtra("fatLevel",parametersBean.getFatLevel());
                intent.putExtra("basalMetabolism",parametersBean.getBasalMetabolism());
                intent.putExtra("muscle",parametersBean.getMuscle());
                intent.putExtra("bmi",parametersBean.getBmi());
                intent.putExtra("createDate",parametersBean.getCreateDate());
                intent.putExtra("protein",parametersBean.getProtein());
                intent.putExtra("bodyFatRateReference",parametersBean.getBodyFatRateReference());
                intent.putExtra("muscleRateReference",parametersBean.getMuscleRateReference());
                intent.putExtra("basalMetabolismReference",parametersBean.getBasalMetabolismReference());
                intent.putExtra("bmiReference",parametersBean.getBmiReference());
                intent.putExtra("boneSaltReference",parametersBean.getBoneSaltReference());
                intent.putExtra("fatLevelReference",parametersBean.getFatLevelReference());
                intent.putExtra("muscleReference",parametersBean.getMuscleReference());
                intent.putExtra("proteinReference",parametersBean.getProteinReference());
                intent.putExtra("waterContentReference",parametersBean.getWaterContentReference());
                intent.putExtra("BoneSaltReference",parametersBean.getBoneSaltReference());

                mContext.startActivity(intent);




            }
        });


    }

    @Override
    public void onBindDataForItem(MyViewHodler viewHolder, TrendListUserBean.DataBean.ParametersBean bean, int position, List<TrendListUserBean.DataBean.ParametersBean> mData) {
        String[] array = bean.getCreateDate().split(" ");

        if (position==0) {
            viewHolder.tvTimeMonth.setText(array[0]);
            viewHolder.tvWeek.setText(TimeUtil.dateToWeek(bean.getCreateDate()));
            viewHolder.tvTime.setText(array[1]);
            viewHolder.tvWeightNum.setText(Double.valueOf(bean.getWeight())*2+"");
            viewHolder.tvPercentage.setText(bean.getBodyFatRate());
            viewHolder.viewBack.setVisibility(View.VISIBLE);
            viewHolder.linearlayout.setVisibility(View.VISIBLE);
        }else {

            if (mData.get(position).getCreateDate().split(" ")[0].equals(mData.get(position-1).getCreateDate().split(" ")[0])) {
                viewHolder.viewBack.setVisibility(View.GONE);
                viewHolder.linearlayout.setVisibility(View.GONE);
                viewHolder.tvTime.setText(bean.getCreateDate());
                viewHolder.tvWeightNum.setText(Double.valueOf(bean.getWeight())*2+"");
                viewHolder.tvPercentage.setText(bean.getBodyFatRate());
            }else {
                viewHolder.tvTimeMonth.setText(array[0]);
                viewHolder.tvWeek.setText(TimeUtil.dateToWeek(bean.getCreateDate()));
                viewHolder.viewBack.setVisibility(View.VISIBLE);
                viewHolder.linearlayout.setVisibility(View.VISIBLE);
                viewHolder.tvTime.setText(array[1]);
                viewHolder.tvWeightNum.setText(Double.valueOf(bean.getWeight())*2+"");
                viewHolder.tvPercentage.setText(bean.getBodyFatRate());

            }

        }


    }


    public class MyViewHodler extends BaseViewHolder {

        TextView  tvTimeMonth;
        TextView tvWeek;
        View viewBack;
        LinearLayout linearlayout;
        TextView tvTime;
        TextView tvWeightNum;
        TextView tvPercentage;
        ConstraintLayout ctl_date;



        public MyViewHodler(View itemView) {
            super(itemView);
            tvTimeMonth = itemView.findViewById(R.id.tvTimeMonth);
            tvWeek = itemView.findViewById(R.id.tvWeek);
            viewBack = itemView.findViewById(R.id.viewBack);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvWeightNum = itemView.findViewById(R.id.tv_weight_num);
            tvPercentage = itemView.findViewById(R.id.tv_percentage);
            ctl_date = itemView.findViewById(R.id.ctl_date);
            linearlayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}
