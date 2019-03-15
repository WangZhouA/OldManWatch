package com.saiyi.oldmanwatch.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;

/**
 * @Author liwenbo
 * @Date 18/9/28
 * @Describe
 */
public class MyItemView extends LinearLayout {
    private ImageView mIvIcon = null;
    private ImageView mIvRight = null;
    private TextView mTvTitle = null;
    private Context mContext;

    public MyItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_my_item, this, true);
        mContext = context;
        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mIvRight = (ImageView) findViewById(R.id.iv_right);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
    }

    public void setIconRes(int resId) {
        mIvIcon.setImageResource(resId);
    }

    public void setRightRes(int resId) {
        mIvRight.setImageResource(resId);
    }

    public void setText(String str) {
        mTvTitle.setText(str);
    }

}
