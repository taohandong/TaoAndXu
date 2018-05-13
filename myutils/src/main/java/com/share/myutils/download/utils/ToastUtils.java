package com.share.myutils.download.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ToaHanDong on 2017/12/28.
 */

public class ToastUtils {
    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                .show();
    }

    public static void showToast(Context context, String msg, int time){
        if (time <= 0){
            time = Toast.LENGTH_SHORT;
        }
        Toast.makeText(context, msg, time)
                .show();
    }
}
