package com.saiyi.oldmanwatch.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 最帅的男人 on 2019/2/19.
 */
public class MainTitleActivity extends BaseAppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.im_ceng)
    ImageView imCeng;
    @BindView(R.id.im_dc)
    ImageView imDc;
    @BindView(R.id.btn_ceng)
    TextView btnCeng;

    private AlphaAnimation alphaAnimation;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_gif_hide;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

        imDc.setAlpha(0.0f);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1500);    //深浅动画持续时间

//      alphaAnimation.setFillAfter(true);   //动画结束时保持结束的画面
        imDc.setImageResource(R.mipmap.gf);
        imDc.setAlpha(1.0f);
        imDc.setAnimation(alphaAnimation);
        alphaAnimation.start();



    }


    @OnClick(R.id.btn_ceng)
    public void onViewClicked() {

       startActivity( new Intent(MainTitleActivity.this,ScalesActivity.class));
       finish();

    }
}
