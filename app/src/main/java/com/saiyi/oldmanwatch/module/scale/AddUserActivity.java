package com.saiyi.oldmanwatch.module.scale;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.dialog.DialogListener;
import com.saiyi.oldmanwatch.dialog.DialogUtils;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.TimeUtil;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import fule.com.mydatapicker.DataPickerDialog;
import fule.com.mydatapicker.DatePickerDialog;
import fule.com.mydatapicker.DateUtil;

import static com.saiyi.oldmanwatch.helper.RetrofitService.ADD_USER;

/**
 * Created by 最帅的男人 on 2019/2/14.
 */
public class AddUserActivity extends BaseAppCompatActivity implements HttpRequestCallback{

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.imNameB)
    ImageView imNameB;
    @BindView(R.id.tvHight)
    TextView tvHight;
    @BindView(R.id.imHightB)
    ImageView imHightB;
    @BindView(R.id.rl_height)
    RelativeLayout rlHeight;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.imSexB)
    ImageView imSexB;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tvbirth)
    TextView tvbirth;
    @BindView(R.id.imbirthB)
    ImageView imbirthB;
    @BindView(R.id.rl_birth)
    RelativeLayout rlBirth;
    @BindView(R.id.rl_name)
    RelativeLayout rl_name;

    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    private Dialog dateDialog; // 时间
    private Dialog chooseDialog;  //性别
    private Dialog heightDialog;  //身高

    private List<String> strSex = new ArrayList<>(); //性别

    private List<String> heightlist = new ArrayList<>(); //身高


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_user;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        tvBarLeft.setBackgroundResource(R.mipmap.back_b);
        tvBarTitle.setText(getResources().getString(R.string.addMembers));

    }

    @Override
    protected void initData() {
        strSex.add("男");
        strSex.add("女");


        //填充身高
        for (int i = 50; i <= 300; i++) {
            heightlist.add(i +"");
        }


    }


    @OnClick({R.id.tv_BarLeft, R.id.rl_height, R.id.rl_sex, R.id.rl_birth, R.id.rl_name,R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:

                okHttpAddUser();

                break;
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.rl_height:

                showChooseDialogHeight(heightlist);

                break;
            case R.id.rl_sex:

                showChooseDialog(strSex);

                break;
            case R.id.rl_birth:
                showDateDialog(DateUtil.getDateForString(TimeUtil.getCurrentTime()));
                break;
            case R.id.rl_name:

                DialogUtils.showDialogEditText(this, R.string.edName, new DialogListener() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onCompleteDate(String str) {

                        tvName.setText(str);

                    }
                }, 0);

                break;
        }
    }


    /**
     * 显示日期
     *
     * @param date
     */
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                tvbirth.setText(String.format("%d-%s-%s", dates[0], dates[1] > 9 ? dates[1] : ("0" + dates[1]), dates[2] > 9 ? dates[2] : ("0" + dates[2])));
            }

            @Override
            public void onCancel() {

            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);
        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    /**
     *  性别
     * */

    /**
     * 显示其他列表
     */
    private void showChooseDialog(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        chooseDialog = builder.setData(mlist).setSelection(1).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        tvSex.setText(itemValue);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
        chooseDialog.show();
    }

    private void showChooseDialogHeight(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(this);
        heightDialog = builder.setData(mlist).setSelection(1).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        tvHight.setText(itemValue);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();
        heightDialog.show();
    }



    private  void   okHttpAddUser() {
        if (TextUtils.isEmpty(tvName.getText().toString())) {
            ToastUtils.show(this, "姓名不能为空", ToastUtils.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(tvHight.getText().toString())) {
            ToastUtils.show(this, "身高不能为空", ToastUtils.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(tvSex.getText().toString())) {
            ToastUtils.show(this, "性别不能为空", ToastUtils.LENGTH_SHORT);
        } else if (TextUtils.isEmpty(tvbirth.getText().toString())) {
            ToastUtils.show(this, "生日不能为空", ToastUtils.LENGTH_SHORT);
        } else{
            Map<String, String> maps = new HashMap<>();
            maps.put("birthday", tvbirth.getText().toString());
            maps.put("height", tvHight.getText().toString());
            maps.put("name", tvName.getText().toString());
            maps.put("sex", tvSex.getText().toString());
            maps.put("id", (int) SharedPreferencesHelper.get("uid", -1) + "");
            maps.put("headUrl", "123");
            HttpUtils.getInstance(this).postAsynHttp(ADD_USER, maps, 0x01, this);

        }
    }

    @Override
    public void onResponse(String sequest, int type) {

        switch (type){

            case 0x01:

                Toast.makeText(AddUserActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();

                break;

        }

    }

    @Override
    public void onFailure(String exp) {

    }
}
