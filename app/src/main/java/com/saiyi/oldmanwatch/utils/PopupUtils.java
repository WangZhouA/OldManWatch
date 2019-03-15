package com.saiyi.oldmanwatch.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.onFinshListener;
import com.saiyi.oldmanwatch.module.main.MainActivity;
import com.saiyi.oldmanwatch.module.main.PositionerMainActivity;
import com.saiyi.oldmanwatch.module.main.ScalesActivity;
import com.zyyoona7.popup.EasyPopup;

/**
 * Created by 最帅的男人 on 2019/2/20.
 */
public class PopupUtils implements View.OnClickListener{

    private EasyPopup mPopup;
    TextView tv_dingweiqi,tv_watch,tv_sacle;

    Context mContext;


    private static volatile PopupUtils instance=null;


    onFinshListener  onFinshListener;

    public void setOnFinshListener(onFinshListener onFinshListener) {
        this.onFinshListener = onFinshListener;
    }

    public static  PopupUtils getInstance(Context context){
        if(instance==null){
            synchronized(PopupUtils.class){
                if(instance==null){
                    instance=new PopupUtils (context);
                }
            }
        }
        return instance;
    }

    private  PopupUtils(Context context){
        initDate(context);
        mContext = context;
    }



    public  void  initDate(Context context){
        mPopup = EasyPopup.create()
                .setContentView(context, R.layout.popup_qiehuan)
//                .setAnimationStyle(R.style.RightPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .apply();

        tv_dingweiqi = mPopup.findViewById(R.id.tv_dingweiqi);
        tv_watch = mPopup.findViewById(R.id.tv_watch);
        tv_sacle = mPopup.findViewById(R.id.tv_sacle);

        tv_dingweiqi.setOnClickListener(this);
        tv_watch.setOnClickListener(this);
        tv_sacle.setOnClickListener(this);
    }




    public  void  showAsDrop(ImageView view){
        mPopup.showAsDropDown(view);

    }

    public  void  disspoup( ){
        mPopup.dismiss();

    }

    @Override
    public void onClick(View view) {
        int  typeStart = (int)SharedPreferencesHelper.get(SharedPreferencesHelper.DEVICETYPE,-1);
        switch (view.getId()){

            case R.id. tv_dingweiqi:
                if (typeStart!=1) {
                    disspoup();
                    mContext.startActivity(new Intent(mContext, PositionerMainActivity.class));
                    onFinshListener.setFinshListener();

                }else {

                }

                break;
            case R.id. tv_watch:
                if (typeStart!=2) {
                    disspoup();
                    mContext.startActivity(new Intent(mContext, MainActivity.class));
                    onFinshListener.setFinshListener();
                }else {

                }

                break;
            case R.id. tv_sacle:
                if (typeStart!=3) {
                    disspoup();
                    mContext.startActivity(new Intent(mContext, ScalesActivity.class));
                    onFinshListener.setFinshListener();
                }else {

                }
                break;
        }
    }


}
