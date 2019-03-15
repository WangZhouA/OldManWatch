package com.saiyi.oldmanwatch.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.dialog.DialogUtils;
import com.saiyi.oldmanwatch.entity.CustomPush;
import com.saiyi.oldmanwatch.manager.LocalBroadcastManager;
import com.saiyi.oldmanwatch.module.health.activity.TestingActivity;
import com.saiyi.oldmanwatch.module.home.activity.NoticeActivity;
import com.saiyi.oldmanwatch.module.map.fragment.MapFragment;
import com.saiyi.oldmanwatch.module.scale.JiGuangActivity;
import com.saiyi.oldmanwatch.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * @Author liwenbo
 * @Date 18/11/15
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    String value;
    String type;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));


                String msg  = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                JSONObject obj = new JSONObject(msg);
                 type= obj.getString("type");
                 value= obj.getString("values");
                if (type.contains("weight")){
                    String [] array = value.split(",");
                    Intent intentMSG = new Intent(context, JiGuangActivity.class);
                    intentMSG.putExtra("WEI_",array[0]);
                    intentMSG.putExtra("ID_",array[1]);
                    intentMSG.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intentMSG);

                }else if (type.contains("electricity")){

                    DialogUtils.showDialog(context,value, R.string.confirm);
                }else {
                    processCustomMessage(context, bundle);
                }

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
                Intent i = new Intent(context, NoticeActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
//        if (MainActivity.isForeground) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Gson gson = new Gson();
        CustomPush customPush = gson.fromJson(message, CustomPush.class);
        Logger.e("接收到推送下来的自定义消息ssss", "type......." + customPush.getType());
        Logger.e("接收到推送下来的自定义消息ssss", "values....." + customPush.getValues());
        String values[] = customPush.getValues().split(",");
        Intent msgIntent = new Intent();
        switch (customPush.getType()) {
            case "bphrt":
                msgIntent.setAction(TestingActivity.MESSAGE_RECEIVED_ACTION);
                msgIntent.putExtra(TestingActivity.V_HIGH_PRESSURE, values[0]);
                msgIntent.putExtra(TestingActivity.V_LOW_PRESSURE, values[1]);
                msgIntent.putExtra(TestingActivity.V_HEART_RATE, values[2]);
                break;
            case "CR":
                msgIntent.setAction(MapFragment.MESSAGE_RECEIVED_ACTION);
                msgIntent.putExtra(MapFragment.V_LATITUDE, values[0]);
                msgIntent.putExtra(MapFragment.V_LONGITUDE, values[1]);
                msgIntent.putExtra(MapFragment.V_ELECTRIC, values[2]);
                break;

            case "weight":

                Intent intentMSG = new Intent(context, JiGuangActivity.class);
                intentMSG.putExtra("WEI_",values[0]);
                intentMSG.putExtra("ID_",values[1]);
                intentMSG.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intentMSG);

                break;

            case "electricity":

                DialogUtils.showDialog(context,value, R.string.confirm);

                break;


            default:

                break;
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
    }

}
