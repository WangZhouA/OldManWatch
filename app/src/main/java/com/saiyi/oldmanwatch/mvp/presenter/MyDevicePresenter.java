package com.saiyi.oldmanwatch.mvp.presenter;

import android.content.Context;

import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.BasicBean;
import com.saiyi.oldmanwatch.entity.DelDevice;
import com.saiyi.oldmanwatch.entity.DeviceState;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;
import com.saiyi.oldmanwatch.entity.UpdateDeviceBean;
import com.saiyi.oldmanwatch.entity.UploadImgBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.MyDeviceModel;
import com.saiyi.oldmanwatch.mvp.view.MyDeviceView;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class MyDevicePresenter extends BasePresenter<MyDeviceView<DeviceState>> {

    public MyDevicePresenter(MyDeviceView<DeviceState> mView) {
        attachView(mView);
    }

    public void upLoadImg(BaseImpl baseImpl) {
        File file = new File(getView().getUrl());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);
        MultipartBody.Part parts = builder.build().part(0);
        MyDeviceModel.getInstance().upLoadImg(parts, new MyBaseObserver<String>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(String data) {
                getView().getImgUrl(data);
            }
        });
    }

    public void delMyDevice(BaseImpl baseImpl) {
        MyDeviceModel.getInstance().delMyDevice(getView().getDevice(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }

    public void updateSetup(BaseImpl baseImpl) {
        MyDeviceModel.getInstance().updateSetup(getView().getSwitchData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getCode(data.getCode());
            }
        });
    }

    public void updateDevice(BaseImpl baseImpl) {
        MyDeviceModel.getInstance().updateDevice(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getCode(data.getCode());
            }
        });
    }

    public void getDeviceState(BaseImpl baseImpl) {
        MyDeviceModel.getInstance().getDeviceState(getView().getMac(), new MyBaseObserver<DeviceState>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(DeviceState data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

}
