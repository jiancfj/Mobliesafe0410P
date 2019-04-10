package cn.mmvtc.mobliesafe.chapter02.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.mmvtc.mobliesafe.App;

/**
 * Created by Administrator on 2019/4/3.
 * 监听开机启动的广播接收者，用于检查SIM卡是否被更换，
 * 如果被更换则发短息给安全号码。
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG=BootCompleteReceiver.class
            .getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        ((App)context.getApplicationContext()).correctSIM();
    }
}
