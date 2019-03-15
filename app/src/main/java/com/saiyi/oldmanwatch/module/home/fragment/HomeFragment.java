package com.saiyi.oldmanwatch.module.home.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.AllDeviceBean;
import com.saiyi.oldmanwatch.entity.DeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.module.home.activity.NoticeActivity;
import com.saiyi.oldmanwatch.module.home.activity.TelephoneBookActivity;
import com.saiyi.oldmanwatch.module.main.DeviceTypeActivity;
import com.saiyi.oldmanwatch.module.main.MainActivity;
import com.saiyi.oldmanwatch.module.main.PositionerMainActivity;
import com.saiyi.oldmanwatch.module.main.SwitchDevicePop;
import com.saiyi.oldmanwatch.module.user.activity.PerfectPersonalAvtivity;
import com.saiyi.oldmanwatch.mvp.presenter.HomePresenter;
import com.saiyi.oldmanwatch.mvp.view.HomeView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.PopupUtilsList;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.CircleImageView;
import com.saiyi.oldmanwatch.view.ImageTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saiyi.oldmanwatch.helper.RetrofitService.QUERY_ALL_DEVICE_LIST;
import static com.saiyi.oldmanwatch.helper.RetrofitService.USER_INFO;

/**
 * @Author liwenbo
 * @Date 18/9/22
 * @Describe
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeView<DeviceDetailsBean> , HttpRequestCallback{

    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.iv_quantity)
    ImageView ivElectric;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.itv_small_talk)
    ImageTextView itvSmallTalk;
    @BindView(R.id.itv_notice)
    ImageTextView itvNotice;
    @BindView(R.id.itv_conversation)
    ImageTextView itvConversation;
    @BindView(R.id.itv_telephone_book)
    ImageTextView itvTelephoneBook;
    @BindView(R.id.rl_step)
    RelativeLayout rlStep;
    @BindView(R.id.rl_blood_pressure)
    RelativeLayout rlBloodPressure;
    @BindView(R.id.rl_heart_rate)
    RelativeLayout rlHeartRate;
    @BindView(R.id.tv_step_time)
    TextView tvStepTime;
    @BindView(R.id.tv_step_num)
    TextView tvStepNum;
    @BindView(R.id.tv_heart_rate_time)
    TextView tvHeartRateTime;
    @BindView(R.id.tv_heart_rate_num)
    TextView tvHeratRateNum;
    @BindView(R.id.tv_blood_pressure_time)
    TextView tvBloodPressureTime;
    @BindView(R.id.tv_blood_pressure_num)
    TextView tvbloodPressureNum;
    @BindView(R.id.tv_sleep_time)
    TextView tvSleepTime;
    @BindView(R.id.tv_minute_num)
    TextView tvMinuteNum;
    @BindView(R.id.tv_hour_num)
    TextView tvHourNum;


    private Context mContext;
    private static final float MIN_SCALE = .95f;
    private static final float MAX_SCALE = 1.15f;
    private int mMinWidth;
    private int mMaxWidth;
    private int mScreenWidth;
    private static final int REQUEST_CODE_ASK_CALL_PHONE = 1;
    private String mac;
    private String token;
    private int uid;
    private String phone;
    private String filiation;



    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 2:
                    if (Util.ifInclude(queryInformation,"0")==true){
                        Intent intent =new Intent();
                        intent.setClass(mContext, PerfectPersonalAvtivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("age", age);
                        intent.putExtra("sex", sex);
                        intent.putExtra("phone",phones);
                        intent.putExtra("headUrl",headUrl);
                        intent.putExtra("birthday", birthday);
                        intent.putExtra("height", height);
                        mContext.startActivity(intent);

                    }else {

                    }
                    break;
            }
        }
    };



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        itvSmallTalk.setImageResource(R.mipmap.home_notice);
        itvNotice.setImageResource(R.mipmap.home_notice1);
        itvConversation.setImageResource(R.mipmap.home_iphone);
        itvTelephoneBook.setImageResource(R.mipmap.home_book);
        itvSmallTalk.setText(getString(R.string.talk));
        itvNotice.setText(getString(R.string.notice));
        itvConversation.setText(getString(R.string.conversation));
        itvTelephoneBook.setText(getString(R.string.book));
        itvSmallTalk.setTextColor(getResources().getColor(R.color.txt_color));
        itvNotice.setTextColor(getResources().getColor(R.color.txt_color));
        itvConversation.setTextColor(getResources().getColor(R.color.txt_color));
        itvTelephoneBook.setTextColor(getResources().getColor(R.color.txt_color));
        itvSmallTalk.setTextSize(13);
        itvNotice.setTextSize(13);
        itvConversation.setTextSize(13);
        itvTelephoneBook.setTextSize(13);
        initData();

        HttpUtils.getInstance(getActivity()).getSyncHttp(0x03,USER_INFO +  (int) SharedPreferencesHelper.get("uid", 0),this);


        HttpUtils.getInstance(getActivity()).getSyncHttp(0x04,QUERY_ALL_DEVICE_LIST +  (int) SharedPreferencesHelper.get("uid", 0),this);


    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesHelper.put(SharedPreferencesHelper.DEVICETYPE,2);
        mPresenter.getDeviceDetails(this);
        setElectric(BaseApplication.Electric);
    }


    private void initData() {
        mac = CheckUtils.getMac();
        token = (String) SharedPreferencesHelper.get("token", "");
        uid = (int) SharedPreferencesHelper.get("uid", -1);
        filiation = (String) SharedPreferencesHelper.get("filiation", "");
        tvAddress.setText(filiation);
    }

    @OnClick({R.id.itv_conversation, R.id.itv_telephone_book, R.id.itv_notice, R.id.rl_step,
            R.id.rl_blood_pressure, R.id.rl_heart_rate, R.id.civ_pic, R.id.iv_add,R.id.iv_quantity,R.id.tv_quantity})
    public void onViewClicked(View v) {
        Intent intent = new Intent();
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.civ_pic:
//                mPresenter.queryDeviceList(this);

//                PopupUtils.getInstance(getActivity()).showAsDrop(civPic);


                if (allDeviceBeans.size()>0){

                    PopupUtilsList.getInstance(getActivity()).initDate(getActivity(),allDeviceBeans);
                    PopupUtilsList.getInstance(getActivity()).showAsDrop(civPic);
                }

                break;
            case R.id.itv_conversation:
                showDialog();
                break;
            case R.id.itv_telephone_book:
                intent.setClass(mContext, TelephoneBookActivity.class);
                startActivity(intent);
                break;
            case R.id.itv_notice:
                intent.setClass(mContext, NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_step:
                mainActivity.gotoHealth(0);
                break;
            case R.id.rl_blood_pressure:
                mainActivity.gotoHealth(1);
                break;
            case R.id.rl_heart_rate:
                mainActivity.gotoHealth(1);
                break;
            case R.id.iv_add:
                intent.setClass(mContext, DeviceTypeActivity.class);
                startActivity(intent);
                break;
//            case R.id.iv_quantity:
//                intent.setClass(mContext, BindAndUnBindActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.tv_quantity:
//                intent.setClass(mContext, ScanInfoActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    /**
     * 拨打电话DiaLog
     */
    public void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View textEntryView = inflater
                .inflate(
                        R.layout.dialog_relation,
                        null);
        final EditText etCustom = (EditText) textEntryView.findViewById(R.id.et_custom);
        TextView title = textEntryView.findViewById(R.id.tv_custom);
        title.setText("拨打电话");
        etCustom.setHint("输入手机号");
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(
                mContext, android.app.AlertDialog.THEME_HOLO_LIGHT);
        dialog.setView(textEntryView);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if ("".equals(etCustom.getText().toString()) || !CheckUtils.isPhone(etCustom.getText().toString())) {
                    ToastUtils.show(mContext, "手机号不正确！", ToastUtils.LENGTH_LONG);
                    return;
                }
                callPhone(etCustom.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        android.app.AlertDialog ad = dialog.create();
        ad.show();
    }

    /**
     * 电量图标更新
     *
     * @param electric
     */
    public void setElectric(int electric) {
        if (0 < electric && electric <= 25) {
            ivElectric.setImageResource(R.mipmap.electricity4);
        } else if (electric > 25 && electric <= 50) {
            ivElectric.setImageResource(R.mipmap.electricity3);
        } else if (electric > 50 && electric <= 75) {
            ivElectric.setImageResource(R.mipmap.electricity2);
        } else {
            ivElectric.setImageResource(R.mipmap.electricity1);
        }
        tvQuantity.setText(electric == 0 ? "100%" : electric + "%");
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        phone = phoneNum;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.READ_LOGS}, REQUEST_CODE_ASK_CALL_PHONE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            startActivity(intent);
        }
    }

    //动态权限申请后处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                // Permission Granted callDirectly(mobile);

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                startActivity(intent);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onRequestSuccessData(DeviceDetailsBean data) {
        DeviceDetailsBean mDevice = data;
        Glide.with(mContext).load(Constant.getRoot() + mDevice.getHeadUrl()).error(Util.getRes(filiation)).into(civPic);
        tvStepTime.setText(mDevice.getStepNumber().getStepsDate());
        tvStepNum.setText(mDevice.getStepNumber().getSteps());
        tvHeartRateTime.setText(mDevice.getHeartPressure().getHeartDate());
        tvHeratRateNum.setText(mDevice.getHeartPressure().getHeart());
        tvBloodPressureTime.setText(mDevice.getHeartPressure().getBloodDate());
        tvbloodPressureNum.setText(mDevice.getHeartPressure().getPressure());
    }

    @Override
    public void getDevices(List<QueryDeviceList> list) {
        SwitchDevicePop mPop = new SwitchDevicePop(mContext, list, civPic);
        mPop.show(civPic);
        mPop.setItemSelectListener(new SwitchDevicePop.itemSelectListener() {
            @Override
            public void getDevice(QueryDeviceList info) {
                Intent intent = new Intent();
                switch (info.getType()) {
                    case "1":
                        intent.setClass(mContext, MainActivity.class);
                        SharedPreferencesHelper.put("mac", info.getMac());
                        SharedPreferencesHelper.put("heardUrl", info.getHeadUrl() == null ? "" : info.getHeadUrl());
                        SharedPreferencesHelper.put("filiation", info.getFiliation());
                        SharedPreferencesHelper.put("type", info.getType());
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case "2":
                        intent.setClass(mContext, PositionerMainActivity.class);
                        SharedPreferencesHelper.put("mac", info.getMac());
                        SharedPreferencesHelper.put("heardUrl", info.getHeadUrl() == null ? "" : info.getHeadUrl());
                        SharedPreferencesHelper.put("filiation", info.getFiliation());
                        SharedPreferencesHelper.put("type", info.getType());
                        startActivity(intent);
                        getActivity().finish();
                        break;
                }
            }
        });
    }

    @Override
    public QueryDeviceDetailsBean getQueryDeviceDetails() {
        QueryDeviceDetailsBean data = new QueryDeviceDetailsBean();
        data.setMac(mac);
        data.setUid(uid);
        return data;
    }

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public String getToken() {
        return token;
    }

    List<String> queryInformation = new ArrayList<>(); // 查询用户信息是否完整。1，代表 完整  0 代表不完整。


    String name;
    String age;
    String sex;
    String phones;
    String headUrl;
    String birthday;
    String height;

    private List<AllDeviceBean> allDeviceBeans=new ArrayList<>();
    @Override
    public void onResponse(String sequest, int type) {

        switch (type){

            case  0x03:
                queryInformation.clear();
                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONObject itemOj = object.getJSONObject("data");

                    Log.e("http", itemOj.getString("name"));

                    name=itemOj.getString("name");
                    age=itemOj.getString("age");
                    sex=itemOj.getString("sex");
                    phones=itemOj.getString("phone");
                    headUrl=itemOj.getString("headUrl");
                    birthday=itemOj.getString("birthday");
                    height=itemOj.getString("height");


                    if (TextUtils.isEmpty(sex)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }
                    if (TextUtils.isEmpty(birthday)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }
                    if (TextUtils.isEmpty(height)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessageDelayed(2,100);
                break;

            case 0x04:

                allDeviceBeans.clear();
                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONArray array = object.getJSONArray("data");
                    for (int i=0;i<array.length();i++){

                        AllDeviceBean allDeviceBean =new AllDeviceBean();
                        JSONObject itemOj = array.getJSONObject(i);
                        allDeviceBean.setFiliation(itemOj.getString("filiation"));
                        allDeviceBean.setHeadUrl(itemOj.getString("headUrl"));
                        allDeviceBean.setType(itemOj.getString("type"));
                        allDeviceBean.setMac(itemOj.getString("mac"));
                        allDeviceBeans.add(allDeviceBean);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("---->allDeviceBeans",allDeviceBeans.get(0).getFiliation());
                mHandler.sendEmptyMessageDelayed(3,100);

                break;


        }

    }


    @Override
    public void onFailure(String exp) {

    }
}
