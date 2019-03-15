package com.saiyi.oldmanwatch.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saiyi.oldmanwatch.http.callback.BaseImpl;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jack on 2017/6/14
 */

public abstract class BaseMvpFragment<PRESENTER extends BasePresenter> extends Fragment implements BaseImpl {

    private static final String TAG = "BaseFragment";

    /**
     * @Method getLayoutId
     * @Describe 获得布局Id
     * @Params int
     */
    protected abstract int getLayoutId();

    protected abstract void onViewReallyCreated(View view);


    protected PRESENTER mPresenter;

    private CompositeDisposable mCompositeDisposable;
    private View rootView;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (null == mPresenter) {
            mPresenter = createPresenter();
        }
        if (null == rootView) {
            rootView = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, rootView);//注解框架
            onViewReallyCreated(rootView);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    protected PRESENTER createPresenter() {
        return null;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public <VIEW extends View> VIEW findView(int id) {
        if (null != rootView) {
            View child = rootView.findViewById(id);
            try {
                return (VIEW) child;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "findView: " + String.valueOf(e.getMessage()));
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean addDisposable(Disposable disposable) {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.add(disposable);
        }
        return true;
    }
}