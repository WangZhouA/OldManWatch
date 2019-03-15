package com.saiyi.oldmanwatch.base;

import android.text.TextUtils;
import android.util.Log;

import com.saiyi.oldmanwatch.helper.RetrofitService;
import com.saiyi.oldmanwatch.http.BaseRetrofit;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 2017/6/13
 */

public class BaseModel extends BaseRetrofit {

    private static final String TAG = "BaseModel";

    protected RetrofitService mServletApi;

    protected Map<String, String> mParams = new HashMap<>();

    public BaseModel() {
        super();
        mServletApi = mRetrofit.create(RetrofitService.class);
    }

    @Override
    protected Map<String, String> getCommonMap() {
        Map<String, String> commonMap = new HashMap<>();
        return commonMap;
    }

    protected void addParams(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            Log.e(TAG, "the key is null");
            return;
        }
        mParams.put(key, value);
    }

    protected void addParams(Map<String, String> params) {
        if (null == params) {
            Log.e(TAG, "the map is null");
            return;
        }
        mParams.putAll(params);
    }
}
