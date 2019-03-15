package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.FeedbackModel;
import com.saiyi.oldmanwatch.mvp.view.FeedbackView;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author liwenbo
 * @Date 18/10/25
 * @Describe
 */
public class FeedbackPresenter extends BasePresenter<FeedbackView<BaseResponse>> {

    public FeedbackPresenter(FeedbackView<BaseResponse> mView) {
        attachView(mView);
    }

    public void upLoadImg(BaseImpl baseImpl) {
        File file = new File(getView().getUrl());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), imageBody);
        MultipartBody.Part parts = builder.build().part(0);
        FeedbackModel.getInstance().upLoadImg(parts, new MyBaseObserver<String>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(String data) {
                getView().getImgUrl(data);
            }
        });
    }

    public void addOpinion(BaseImpl baseImpl) {
        FeedbackModel.getInstance().addOpinion(getView().getData(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
