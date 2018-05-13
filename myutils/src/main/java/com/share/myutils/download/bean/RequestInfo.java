package com.share.myutils.download.bean;

import android.support.annotation.IntRange;

import com.share.myutils.download.config.InnerConstant;
import com.share.myutils.download.utils.DebugUtils;

import java.io.Serializable;

/**
 * Created by ToaHanDong on 2017/12/28.
 */

public class RequestInfo implements Serializable {

    //请求下载到暂停
    @IntRange(from = InnerConstant.Request.loading, to = InnerConstant.Request.pause)
    private int dictate;   //下载的控制状态

    private DownloadInfo downloadInfo;

    public RequestInfo() {
    }



    public int getDictate() {
        return dictate;
    }

    public void setDictate(int dictate) {
        this.dictate = dictate;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }


    @Override
    public String toString() {
        return "RequestInfo{" +
                "dictate=" + DebugUtils.getRequestDictateDesc(dictate) +
                ", downloadInfo=" + downloadInfo +
                '}';
    }

}
