package com.saiyi.oldmanwatch.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.SystemBarTintManager;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jack on 2017/6/13
 */

public abstract class BaseMvpAppCompatActivity<PRESENTER extends BasePresenter> extends FragmentActivity implements BaseImpl {

    private CompositeDisposable mCompositeDisposable;

    protected PRESENTER mPresenter;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        if (null == mPresenter) {
            mPresenter = createPresenter();
        }
        if (this.getLayoutId() < 0) {
        }
        this.setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        new SharedPreferencesHelper(this);
        type = (String) SharedPreferencesHelper.get("type", "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            if ("2".equals(type))
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.appColor_position));
            else
                tintManager.setStatusBarTintColor(getResources().getColor(R.color.appColor));

        }
        this.initViews(savedInstanceState);//初始化用户视图
        this.initData();//初始化数据
    }

    // 设置状态栏颜色
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected PRESENTER createPresenter() {
        return null;
    }


    @Override
    public boolean addDisposable(Disposable disposable) {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.add(disposable);
        }
        return true;
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * @Method getLayoutId
     * @Describe 获得布局Id
     * @Params void
     */
    protected abstract int getLayoutId();

    /**
     * @Method $
     * @Describe 根据Id找到对应控件对象
     * @Params id 控件Id
     */
    @SuppressWarnings("unchecked")
    protected <V extends View> V $(int id) {
        return (V) this.findViewById(id);
    }

    /**
     * @Method initView
     * @Describe 初始化用户界面
     * @Params savedInstanceState 参数
     */
    protected abstract void initViews(Bundle savedInstanceState);


    /**
     * @Method initData
     * @Describe 初始化用户数据
     * @Params void
     */
    protected abstract void initData();

    /**
     * @Method startActivity
     * @Describe 覆盖startActivity(Intent intent)
     * @Params intent 需要启动的意图
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    /**
     * @Method startActivity
     * @Describe 重载startActivity
     * @Params intent 要启动的意图
     * @Params options 需要传输的参数
     */
    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
    }

    /**
     * @Method startActivityForResult
     * @Describe 覆盖startActivityForResult
     * @Params intent 需要启动的意图
     * @Params requestCode 请求码
     * @Params options 参数
     */
    @SuppressLint({"NewApi", "RestrictedApi"})
    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    /**
     * @Method startActivityForResult
     * @Describe 覆盖startActivityForResult
     * @Params intent 需要启动的意图
     * @Params requestCode 请求码
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * @Method finish
     * @Describe 关闭当前Activity
     * @Params void
     */
    @Override
    public void finish() {
        super.finish();
    }

    /**
     * @Method onDestroy
     * @Describe 生命周期(销毁)
     * @Params void
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    /**
     * @Method showToast
     * @Describe 弹出吐司
     * @Params msg 需要弹出显示的内容
     */
    public void showToast(String msg) {
        this.showToast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * @Method showToast
     * @Describe 弹出吐司
     * @Params msg 弹出内容
     * @Params duration 弹出时间长短
     */
    public void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this, msg, duration);
        } else {
            ToastUtils.show(this, msg, ToastUtils.LENGTH_SHORT);
        }
    }

    /**
     * @Method showToast
     * @Describe 弹出吐司
     * @Params resId 需要显示的布局
     */
    public void showToast(int resId) {
        this.showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * @Method showToast
     * @Describe 弹出吐司
     * @Params resId 需要显示的布局
     * @Params duration 弹出显示的时间
     */
    public void showToast(int resId, int duration) {
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(this, resId, duration);
        } else {
            ToastUtils.show(this, resId, ToastUtils.LENGTH_SHORT);
        }
    }

}
