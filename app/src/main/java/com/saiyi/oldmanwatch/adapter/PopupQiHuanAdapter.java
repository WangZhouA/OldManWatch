package com.saiyi.oldmanwatch.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.entity.AllDeviceBean;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/3/8.
 */
public class PopupQiHuanAdapter extends AbsBaseAdapter<AllDeviceBean,PopupQiHuanAdapter.PopupViewHolder> {


    private Context mContext;

    public PopupQiHuanAdapter(Context context) {
        super(context, R.layout.item_popup);
        mContext= context;
    }

    @Override
    public PopupQiHuanAdapter.PopupViewHolder onCreateVH(View itemView, int ViewType) {
        return  new PopupViewHolder(itemView);
    }

    @Override
    public void onBindDataForItem(PopupQiHuanAdapter.PopupViewHolder viewHolder, AllDeviceBean bean, int position, List<AllDeviceBean> mData) {


        if (bean.getType().equals("1")) {


                viewHolder.im_pic.setImageResource(R.mipmap.watch);


        } else if (bean.getType().equals("2")) {


                viewHolder.im_pic.setImageResource(R.mipmap.locator);

        } else {

                viewHolder.im_pic.setImageResource(R.mipmap.scale);

        }

        viewHolder.tv_dingweiqi.setText(bean.getFiliation());


    }


    public  class  PopupViewHolder extends BaseViewHolder{

        ImageView im_pic;
        TextView  tv_dingweiqi;
        LinearLayout lin_device;

        public PopupViewHolder(View itemView) {
            super(itemView);

            im_pic=  itemView.findViewById(R.id.im_pic);
            tv_dingweiqi=  itemView.findViewById(R.id.tv_dingweiqi);
            lin_device=  itemView.findViewById(R.id.lin_device);

        }
    }
}
