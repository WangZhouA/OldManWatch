package com.saiyi.oldmanwatch.module.map.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.view.ImageTextView;

/**
 * @Author liwenbo
 * @Date 18/11/8
 * @Describe
 */
public class MapPop extends PopupWindow {

    private Context mContext;
    private ImageTextView itvMapBasic;
    private ImageTextView itvMapSatellite;
    private ImageTextView itvMapBus;
    private View mMenuView;
    private LayoutInflater inflater = null;
    private LinearLayout popupLL;
    private int type = 0;
    private typeSelectListener mTypeSelectListener;

    public MapPop(Context context, int type) {
        mContext = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_map, null);
        this.type = type;
        initWidget();
        setPopup();

    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        itvMapBasic = mMenuView.findViewById(R.id.itv_map_basic);
        itvMapSatellite = mMenuView.findViewById(R.id.itv_map_satellite);
        itvMapBus = mMenuView.findViewById(R.id.itv_map_bus);
        popupLL = mMenuView.findViewById(R.id.ll_home);
        itvMapBasic.setText(mContext.getResources().getString(R.string.map_basic));
        itvMapSatellite.setText(mContext.getResources().getString(R.string.map_satellite));
        itvMapBus.setText(mContext.getResources().getString(R.string.map_bus));
        itvMapBasic.setTextSize(13);
        itvMapSatellite.setTextSize(13);
        itvMapBus.setTextSize(13);

        switch (type) {
            case 1:
                itvMapBasic.setTextColor(mContext.getResources().getColor(R.color.appColor));
                itvMapSatellite.setTextColor(mContext.getResources().getColor(R.color.txt_color));
                itvMapBus.setTextColor(mContext.getResources().getColor(R.color.txt_color));
                itvMapBasic.setImageResource(R.mipmap.map1_ed);
                itvMapSatellite.setImageResource(R.mipmap.map2);
                itvMapBus.setImageResource(R.mipmap.map3);
                break;
            case 2:
                itvMapBasic.setTextColor(mContext.getResources().getColor(R.color.txt_color));
                itvMapSatellite.setTextColor(mContext.getResources().getColor(R.color.appColor));
                itvMapBus.setTextColor(mContext.getResources().getColor(R.color.txt_color));
                itvMapBasic.setImageResource(R.mipmap.map1);
                itvMapSatellite.setImageResource(R.mipmap.map_ed);
                itvMapBus.setImageResource(R.mipmap.map3);
                break;
            case 3:
                itvMapBasic.setTextColor(mContext.getResources().getColor(R.color.txt_color));
                itvMapSatellite.setTextColor(mContext.getResources().getColor(R.color.txt_color));
                itvMapBus.setTextColor(mContext.getResources().getColor(R.color.appColor));
                itvMapBasic.setImageResource(R.mipmap.map1);
                itvMapSatellite.setImageResource(R.mipmap.map2);
                itvMapBus.setImageResource(R.mipmap.map3_ed);
                break;
        }

        itvMapBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeSelectListener.getType(1);
                itvMapBasic.setTextColor(R.color.appColor);
                itvMapSatellite.setTextColor(R.color.txt_color);
                itvMapBus.setTextColor(R.color.txt_color);
                itvMapBasic.setImageResource(R.mipmap.map1_ed);
                itvMapSatellite.setImageResource(R.mipmap.map2);
                itvMapBus.setImageResource(R.mipmap.map3);
                dismiss();
            }
        });
        itvMapSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeSelectListener.getType(2);
                itvMapBasic.setTextColor(R.color.txt_color);
                itvMapSatellite.setTextColor(R.color.appColor);
                itvMapBus.setTextColor(R.color.txt_color);
                itvMapBasic.setImageResource(R.mipmap.map1);
                itvMapSatellite.setImageResource(R.mipmap.map_ed);
                itvMapBus.setImageResource(R.mipmap.map3);
                dismiss();
            }
        });
        itvMapBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTypeSelectListener.getType(3);
                itvMapBasic.setTextColor(R.color.txt_color);
                itvMapSatellite.setTextColor(R.color.txt_color);
                itvMapBus.setTextColor(R.color.appColor);
                itvMapBasic.setImageResource(R.mipmap.map1);
                itvMapSatellite.setImageResource(R.mipmap.map2);
                itvMapBus.setImageResource(R.mipmap.map3_ed);
                dismiss();
            }
        });
    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(mMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        this.setAnimationStyle(R.style.AnimTopRight);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x33000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = popupLL.getBottom();
                int left = popupLL.getLeft();
                int right = popupLL.getRight();
                int y = (int) event.getY();
                int x = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height || x < left || x > right) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void show(View view) {

        showAsDropDown(view, 0, 0);
    }

    public interface typeSelectListener {
        void getType(int type);
    }

    public void setTypeSelectListener(typeSelectListener typeSelectListener) {
        this.mTypeSelectListener = typeSelectListener;
    }
}
