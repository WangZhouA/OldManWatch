package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.AddOpinionBean;
import com.saiyi.oldmanwatch.entity.BasicBean;
import com.saiyi.oldmanwatch.entity.UploadImgBean;

/**
 * @Author liwenbo
 * @Date 18/10/25
 * @Describe
 */
public interface FeedbackView<T> extends BaseRequestContract<T> {
    void getImgUrl(String url);

    AddOpinionBean getData();

    String getUrl();
}
