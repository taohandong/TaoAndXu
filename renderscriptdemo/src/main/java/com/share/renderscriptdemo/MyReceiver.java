package com.share.renderscriptdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ToaHanDong on 2018/1/25.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context,HouTaiService.class);
        context.startService(intent1);
    }
}
