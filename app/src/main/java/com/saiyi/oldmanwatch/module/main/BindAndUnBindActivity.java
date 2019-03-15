package com.saiyi.oldmanwatch.module.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.dialog.DialogListener;
import com.saiyi.oldmanwatch.dialog.DialogUtils;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.module.scale.UserMangerActivity;
import com.saiyi.oldmanwatch.utils.MyDialog;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.yzq.zxinglibrary.encode.CodeCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saiyi.oldmanwatch.helper.RetrofitService.HAND_OVER_MANAGER;
import static com.saiyi.oldmanwatch.helper.RetrofitService.QUERY_USER_TYPE;
import static com.saiyi.oldmanwatch.helper.RetrofitService.UNBINDDEVICE;

/**
 * Created by 最帅的男人 on 2019/2/12.
 */
public class BindAndUnBindActivity extends BaseAppCompatActivity implements HttpRequestCallback{
    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.title_Top)
    TextView titleTop;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.tv_unBind)
    TextView tvUnBind;

    @BindView(R.id.tv_people)
    TextView tv_people;

    Bitmap bitmap = null;

    String iME = ""; //暂时假设一个字符串


    MyDialog myDialog;


    private int  deviceType  =3;  // 这设备是什么类型的


    private int   userTypeManger = 0; //  判断你是不是管理员


    @Override
    protected int getLayoutId() {
        return R.layout.activity_unbind;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(getResources().getString(R.string.bind_unBind));

    }

    @Override
    protected void initData() {

        iME = getIntent().getStringExtra("mac");

        if (GenerateTheQrCode(iME) != null) {
            ivScan.setImageBitmap(bitmap);
            titleCenter.setText(getResources().getString(R.string.device_ime) + iME);
        }

        //先要去查下接口啊，判断下是不是管理员

        okHttpQueryType();//查询自己是不是管理


    }

    private void okHttpQueryType() {

        Map<String,String> maps = new HashMap<>();
        maps.put("mac",iME);
        maps.put("uid",SharedPreferencesHelper.get("uid", -1)+"");
        HttpUtils.getInstance(this).postAsynHttp(QUERY_USER_TYPE,maps,0x03,this);
    }


    @OnClick({R.id.tv_BarLeft, R.id.tv_unBind, R.id.tv_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_unBind:

                if (userTypeManger==1) {
                    showDialog();
                }else {
                    okHttpUnbind(deviceType);
                }
                break;

            case R.id.tv_people:
                Intent mIntent = new Intent(this, UserMangerActivity.class).putExtra("mac",iME);
                startActivity(mIntent);
                break;
        }
    }


    private Bitmap GenerateTheQrCode(String iME) {

        if (TextUtils.isEmpty(iME)) {
            showToast("请输入要生成二维码图片的字符串");
            return bitmap;
        }
        try {
            bitmap = CodeCreator.createQRCode(iME, 400, 400, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private void showDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_item, null);
        myDialog = new MyDialog(this, view, R.style.MyDialog, 0);
        TextView unBindMe = view.findViewById(R.id.unBindMe);
        TextView unNow = view.findViewById(R.id.unNow);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);

        unBindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(BindAndUnBindActivity.this, UserMangerActivity.class).putExtra("mac",iME).putExtra("T",1);
                startActivityForResult(mIntent,1);
                myDialog.dismiss();
                myDialog.cancel();
            }
        });

        unNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogNow(deviceType);
                myDialog.dismiss();
                myDialog.cancel();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog.dismiss();
                myDialog.cancel();

            }
        });

        myDialog.show();

    }



    // 移交管理员
    private void okHttpHandOverManager(String Chengyuanuid){

        Map<String,String> map =new HashMap<>();
        map.put("id", (int) SharedPreferencesHelper.get("uid", -1)+"");
        map.put("mac",iME);
        map.put("uid",Chengyuanuid);
        HttpUtils.getInstance(this).postAsynHttp(HAND_OVER_MANAGER,map,0X01,this);

    }

    private void showDialogUnBindMe(String name,String Chengyuanuid) {

        DialogUtils.showIsOkDialog(BindAndUnBindActivity.this, getResources().getString(R.string.confirm),
                getResources().getString(R.string.cancel), "权限转移", "确定转移管理员权限给" + name, new DialogListener() {
                    @Override
                    public void onComplete() {
                        okHttpHandOverManager(Chengyuanuid);

                    }

                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onCompleteDate(String str) {

                    }
                }
        );

    }

    // 解除设备了
    private void okHttpUnbind(int type) {

        Map<String,String>maps = new HashMap<>();
        maps.put("mac",iME);
        maps.put("type",type+"");
        maps.put("uid",SharedPreferencesHelper.get("uid", -1)+"");

        HttpUtils.getInstance(this).postAsynHttp(UNBINDDEVICE,maps,0x02,this);

    }


    private void showDialogNow(int type) {

        DialogUtils.showIsOkDialog(BindAndUnBindActivity.this, getResources().getString(R.string.confirm),
                getResources().getString(R.string.cancel), "确定要解绑全部成员吗？", "该操作将解绑全部用户，同时删除其数据。", new DialogListener() {
                    @Override
                    public void onComplete() {

                        //
                        okHttpUnbind(type);



                    }


                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onCompleteDate(String str) {

                    }


                }
        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:

                if (data.getStringExtra("name")!=null) {
                    String mData = data.getStringExtra("name");
                    String uid = data.getStringExtra("id");
                    Log.e("----->mData", mData);
                    showDialogUnBindMe(mData, uid);
                }

                break;

        }
    }


    @Override
    public void onResponse(String sequest, int type) {

                switch (type){

                    case 0x01:

                        ToastUtils.show(BindAndUnBindActivity.this,"移交成功",ToastUtils.LENGTH_SHORT);

                        break;
                    case 0x02:

                        ToastUtils.show(BindAndUnBindActivity.this,"解除成功",ToastUtils.LENGTH_SHORT);

                        break;

                    case 0x03:

                        Log.e("http 进来了么",userTypeManger+"");
                        try {
                            JSONObject object = new JSONObject(sequest);
                            userTypeManger=  object.getInt("data");
                            Log.e("-------->userTypeManger",userTypeManger+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;



        }
    }

    @Override
    public void onFailure(String exp) {
        ToastUtils.show(BindAndUnBindActivity.this,exp.toString(),ToastUtils.LENGTH_SHORT);
    }
}
