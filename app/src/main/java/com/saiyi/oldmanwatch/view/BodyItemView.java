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
public class BodyItemView extends LinearLayout {
    private ImageView mIvIcon = null;
    private TextView mTvRight = null;
    private TextView mTvTitle = null;
    private Context mContext;

    public BodyItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_body_item, this, true);
        mContext = context;
        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
    }

    public void setIconRes(int resId) {
        mIvIcon.setImageResource(resId);
    }

    public void setRightTxt(String str) {
        mTvRight.setText(str);
    }

    public void setText(String str) {
        mTvTitle.setText(str);
    }

}
