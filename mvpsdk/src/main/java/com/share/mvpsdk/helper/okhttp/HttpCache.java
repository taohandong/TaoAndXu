package com.share.mvpsdk.helper.okhttp;


import android.os.Environment;

import com.share.mvpsdk.utils.AppUtils;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        File file=new File(Environment.getExternalStorageDirectory() + File
                .separator + "data/NetCache");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        if (!file.exists())file.mkdirs();
        return new Cache(file,
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
