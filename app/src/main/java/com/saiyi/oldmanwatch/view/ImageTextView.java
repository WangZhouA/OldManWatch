package com.saiyi.oldmanwatch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;


/**
 * @Author liwenbo
 * @Date 18/9/22
 * @Describe 自定义图文混排View
 */
public class ImageTextView extends LinearLayout {
    private ImageView mImgView = null;
    private TextView mTextView = null;
    private Context mContext;

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_image_text, this, true);
        mContext = context;
        mImgView = (ImageView) findViewById(R.id.img);
        mTextView = (TextView) findViewById(R.id.text);
    }

    /*设置图片接口*/
    public void setImageResource(int resId) {
        mImgView.setImageResource(resId);
    }

    /*设置文字接口*/
    public void setText(String str) {
        mTextView.setText(str);
    }

    /*设置文字大小*/
    public void setTextSize(float size) {
        mTextView.setTextSize(size);
    }

    /*设置文字颜色*/
    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

}
