package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.PerfectPersonalModel;
import com.saiyi.oldmanwatch.mvp.view.PerfectPersonalView;
import com.saiyi.oldmanwatch.utils.Logger;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class PerfectPersonalPresenter extends BasePresenter<PerfectPersonalView<String>> {

    public PerfectPersonalPresenter(PerfectPersonalView<String> mView) {
        attachView(mView);
    }


    public void upLoadImg(BaseImpl baseImpl) {
        File file = new File(getView().getUrl());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);
        MultipartBody.Part parts = builder.build().part(0);
        PerfectPersonalModel.getInstance().upLoadImg(parts, new MyBaseObserver<String>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(String data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void updateUser(BaseImpl baseImpl) {
        PerfectPersonalModel.getInstance().updateUser(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }

}
