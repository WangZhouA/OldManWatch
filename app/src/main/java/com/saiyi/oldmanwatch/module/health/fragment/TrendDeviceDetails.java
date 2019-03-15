package com.saiyi.oldmanwatch.module.health.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.adapter.BodyFatScaleAdapter;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.entity.BodyFatScaleBean;
import com.saiyi.oldmanwatch.view.recyclerview.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 最帅的男人 on 2019/2/25.
 */
public class TrendDeviceDetails extends BaseAppCompatActivity {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.civ_pic)
    ImageView civPic;
    @BindView(R.id.tv_body_weight)
    TextView tvBodyWeight;
    @BindView(R.id.tv_use_name)
    TextView tvUseName;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_body_score)
    TextView tvBodyScore;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_body_type)
    TextView tvBodyType;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<BodyFatScaleBean> mDatas = new ArrayList<>();
    private List<String> titles;

    BodyFatScaleAdapter mAdapter;

    String weight ;
    String score ;
    String size ;
    String bodyFatRate ;
    String muscleRate ;
    String waterContent ;
    String boneSalt ;
    String fatLevel;
    String basalMetabolism;
    String muscle;
    String bmi;
    String createDate;
    String protein;

    int bodyFatRateReference;
    int muscleRateReference;
    int basalMetabolismReference;
    int bmiReference;
    int boneSaltReference;
    int fatLevelReference;
    int muscleReference;
    int proteinReference;
    int waterContentReference;
    int BoneSaltReference;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_trend_device_details;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {


        titles = new ArrayList<>();
        titles.addAll(Arrays.asList(getResources().getStringArray(R.array.body_fat_index)));
        BodyFatScaleBean data = null;
        for (int i = 0; i < titles.size(); i++) {
            data = new BodyFatScaleBean();
            data.setType((i % 5) + "");
            data.setTitle(titles.get(i));
            data.setFigure("0");
            mDatas.add(data);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(TrendDeviceDetails.this, 3));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(1, 1, Color.parseColor("#CBCBCB")));
        mAdapter = new BodyFatScaleAdapter(mDatas, TrendDeviceDetails.this);
        mRecyclerView.setAdapter(mAdapter);


         weight = getIntent().getStringExtra("weight");
         score = getIntent().getStringExtra("score");
         size = getIntent().getStringExtra("size");
         bodyFatRate = getIntent().getStringExtra("bodyFatRate");
         muscleRate = getIntent().getStringExtra("muscleRate");
         waterContent = getIntent().getStringExtra("waterContent");
         boneSalt = getIntent().getStringExtra("boneSalt");
         fatLevel = getIntent().getStringExtra("fatLevel");
         basalMetabolism = getIntent().getStringExtra("basalMetabolism");
         muscle = getIntent().getStringExtra("muscle");
         bmi = getIntent().getStringExtra("bmi");
         createDate = getIntent().getStringExtra("createDate");
        protein = getIntent().getStringExtra("protein");


        bodyFatRateReference= getIntent().getIntExtra("bodyFatRateReference",-1);
        muscleRateReference= getIntent().getIntExtra("muscleRateReference",-1);
        basalMetabolismReference=getIntent().getIntExtra("basalMetabolismReference",-1);
        bmiReference=getIntent().getIntExtra("bmiReference",-1);
        boneSaltReference=getIntent().getIntExtra("boneSaltReference",-1);
        fatLevelReference=getIntent().getIntExtra("fatLevelReference",-1);
        muscleReference=getIntent().getIntExtra("muscleReference",-1);
        proteinReference=getIntent().getIntExtra("proteinReference",-1);
        waterContentReference=getIntent().getIntExtra("waterContentReference",-1);
        BoneSaltReference=getIntent().getIntExtra("BoneSaltReference",-1);


//        <item>体脂率(%)</item>
//        <item>肌肉率(%)</item>
//        <item>水含量(%)</item>
//        <item>骨盐量(kg)</item>
//        <item>内脏脂肪等级</item>
//        <item>基础代谢</item>
//        <item>肌肉重(kg)</item>
//        <item>BMI</item>


        for (int i = 0;i< mDatas.size();i++) {

            if (mDatas.get(i).getTitle().equals(titles.get(0))) { //体脂率
                mDatas.get(i).setFigure(bodyFatRate);
                mDatas.get(i).setType(bodyFatRateReference+"");
            } else if (mDatas.get(i).getTitle().equals(titles.get(1))) { // 肌肉率
                mDatas.get(i).setFigure(muscleRate);
                mDatas.get(i).setType(muscleRateReference+"");
            } else if (mDatas.get(i).getTitle().equals(titles.get(2))) { // 水含量
                mDatas.get(i).setFigure(waterContent);
                mDatas.get(i).setType(waterContentReference+"");
            } else if (mDatas.get(i).getTitle().equals(titles.get(3))) { // 骨盐量
                mDatas.get(i).setFigure(boneSalt);
                mDatas.get(i).setType(BoneSaltReference+"");
            } else if (mDatas.get(i).getTitle().equals(titles.get(4))) { // 内脏脂肪等级
                mDatas.get(i).setFigure(fatLevel);
                mDatas.get(i).setType(fatLevelReference+"");
            } else if (mDatas.get(i).getTitle().equals(titles.get(5))) { // 基础代谢
                mDatas.get(i).setFigure(basalMetabolism);
                mDatas.get(i).setType(basalMetabolismReference+"");
            } else if (mDatas.get(i).getTitle().equals(titles.get(6))) { // 肌肉重
                mDatas.get(i).setFigure(muscle);
                mDatas.get(i).setType(muscleReference+"");
            } else if (mDatas.get(i).getTitle().equals(titles.get(7))) { // bmi
                mDatas.get(i).setFigure(bmi);
                mDatas.get(i).setType(bmiReference+"");

            }else {
                mDatas.get(i).setFigure(protein);
                mDatas.get(i).setType(proteinReference+"");
            }


        }

        mAdapter.setmData(mDatas);


        tvBodyWeight.setText((Double.valueOf(weight)*2)+"");// 体重
        if (TextUtils.isEmpty(score)){

        }else {
            tvBodyScore.setText(score);  //身体得分
        }

        tvTitle.setText(createDate);

        if (size.equals("1")) {
            tvBodyType.setText("偏瘦");
        }else if (size.equals("2")) {
            tvBodyType.setText("正常");
        }else {
            tvBodyType.setText("偏胖");
        }


        civPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


    }

}
