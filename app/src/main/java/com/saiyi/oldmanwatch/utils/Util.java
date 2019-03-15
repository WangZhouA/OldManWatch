package com.saiyi.oldmanwatch.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.saiyi.oldmanwatch.R;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @Author liwenbo
 * @Date 18/10/25
 * @Describe
 */
public class Util {
    /**
     * 获取 APP 详情页面intent
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    /* 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getDaoHangHeight(Context context) {
        int result = 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }

    public  static boolean ifInclude(List<String> list, String  str){
        for(int i=0;i<list.size();i++){
            if (list.get(i).indexOf(str)!=-1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 极光推送设置别名
     *
     * @param context 上下文
     * @param TAG     用户ID。极光标签
     * @param alias   用户别名。手机号
     */
    public static void setAlias(Context context, int TAG, String alias) {
        if (!alias.isEmpty()) {
            JPushInterface.setAlias(context.getApplicationContext(), TAG, alias);//设置别名或标签
        }

    }

    public static int getRes(String filiation) {
        int res = R.mipmap.call1;
        switch (filiation) {
            case "爷爷":
                res = R.mipmap.call1;
                break;
            case "奶奶":
                res = R.mipmap.call2;
                break;
            case "外公":
                res = R.mipmap.call3;
                break;
            case "外婆":
                res = R.mipmap.call4;
                break;
            case "爸爸":
                res = R.mipmap.call5;
                break;
            case "妈妈":
                res = R.mipmap.call6;
                break;
            case "哥哥":
                res = R.mipmap.call7;
                break;
            case "姐姐":
                res = R.mipmap.call8;
                break;
            case "叔叔":
                res = R.mipmap.call9;
                break;
            case "阿姨":
                res = R.mipmap.call10;
                break;
            case "舅舅":
                res = R.mipmap.call11;
                break;
            case "婶婶":
                res = R.mipmap.call12;
                break;
            case "儿子":
                res = R.mipmap.call13;
                break;
            case "女儿":
                res = R.mipmap.call14;
                break;
            case "宠物":
                res = R.mipmap.call15;
                break;
            case "自定义":
                res = R.mipmap.call16;
                break;
        }
        return res;
    }
}
