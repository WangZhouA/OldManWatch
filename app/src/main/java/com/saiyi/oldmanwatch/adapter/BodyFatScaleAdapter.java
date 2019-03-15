package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.BodyFatScaleBean;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/9/27
 * @Describe
 */
public class BodyFatScaleAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<BodyFatScaleBean> mData;
    private Context mContext;

    public BodyFatScaleAdapter(List<BodyFatScaleBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }


    public void setmData(List<BodyFatScaleBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_body_fat_scale, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.height = width / 3;
        view.setLayoutParams(params);
        return new ViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BodyFatScaleBean bean = mData.get(position);

        holder.setText(R.id.tv_numerical_value, mData.get(position).getFigure());
        holder.setText(R.id.tv_title, mData.get(position).getTitle());
        if ("1".equals(mData.get(position).getType())) {
//            holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
//            holder.setText(R.id.tv_type, mContext.getResources().getString(R.string.standard));
//            holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);
//            <item>体脂率(%)</item>
//        <item>肌肉率(%)</item>
//        <item>水含量(%)</item>
//        <item>骨盐量(kg)</item>
//        <item>内脏脂肪等级</item>
//        <item>基础代谢</item>
//        <item>肌肉重(kg)</item>
//        <item>BMI</item>
//        <item>蛋白质</item>
            setTypeStr(bean,1,holder);


        } else if ("2".equals(mData.get(position).getType())) {
//            holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
//            holder.setText(R.id.tv_type, mContext.getResources().getString(R.string.low));
//            holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
            setTypeStr(bean,2,holder);

        } else if ("3".equals(mData.get(position).getType())) {
//            holder.setTextColorRes(R.id.tv_type, R.color.txt_red);
//            holder.setText(R.id.tv_type, mContext.getResources().getString(R.string.high));
//            holder.setBackgroundRes(R.id.tv_type, R.drawable.text_red_shape);

            setTypeStr(bean,3,holder);
        } else if ("4".equals(mData.get(position).getType())) {
//            holder.setTextColorRes(R.id.tv_type, R.color.txt_red);
//            holder.setText(R.id.tv_type, mContext.getResources().getString(R.string.not_up_to_standard));
//            holder.setBackgroundRes(R.id.tv_type, R.drawable.text_red_shape);

            setTypeStr(bean,4,holder);
        } else {
            holder.setTextColorRes(R.id.tv_type, R.color.appWhite);
            holder.setText(R.id.tv_type, mContext.getResources().getString(R.string.no_data));
            holder.setBackgroundRes(R.id.tv_type, R.drawable.text_gray_shape);
        }

    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //
    //flag 是标志位
    private  void  setTypeStr(BodyFatScaleBean bean, int flag ,ViewHolder holder){

        switch (bean.getTitle()){

            case "体脂率(%)":

                switch (flag){

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏低");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);
                        break;

                    case 2:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);

                        break;

                    case 3:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                    case 4:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_red);
                        holder.setText(R.id.tv_type, "过高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_red_shape);

                        break;


                }

                break;


            case "肌肉率(%)":


                switch (flag){

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏低");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);

                        break;

                    case 2:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);
                        break;

                    case 3:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                }



                break;


            case "水含量(%)":


                switch (flag){

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏低");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);

                        break;

                    case 2:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);
                        break;

                    case 3:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                }


                break;


            case "骨盐量(kg)":

                switch (flag){

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏低");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);

                        break;

                    case 2:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);
                        break;

                    case 3:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                }

                break;

            case   "内脏脂肪等级":

                switch (flag) {
                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏低");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);
                        break;

                    case 2:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);

                        break;

                    case 3:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                    case 4:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_red);
                        holder.setText(R.id.tv_type, "过高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_red_shape);

                        break;

                }
                break;

            case "基础代谢":
                switch (flag) {

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "未达标");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);
                        break;

                    case 2:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "已达标");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);

                        break;
                }

                break;


            case "肌肉重(kg)":

                switch (flag){

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏低");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);

                        break;

                    case 2:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);
                        break;

                    case 3:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                }

                break;

            case "BMI":

                switch (flag){

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏瘦");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);
                        break;

                    case 2:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);

                        break;

                    case 3:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏胖");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                    case 4:

                        holder.setTextColorRes(R.id.tv_type, R.color.txt_red);
                        holder.setText(R.id.tv_type, "肥胖");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_red_shape);

                        break;


                }


                break;


            case "蛋白质":

                switch (flag){

                    case 1:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_zise);
                        holder.setText(R.id.tv_type, "偏低");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_zise_shape);

                        break;

                    case 2:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_green);
                        holder.setText(R.id.tv_type, "标准");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_green_shape);
                        break;

                    case 3:
                        holder.setTextColorRes(R.id.tv_type, R.color.txt_yellow);
                        holder.setText(R.id.tv_type, "偏高");
                        holder.setBackgroundRes(R.id.tv_type, R.drawable.text_yellow_shape);
                        break;

                }


                break;



        }


    }


}
