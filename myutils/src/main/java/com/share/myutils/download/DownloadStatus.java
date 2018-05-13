package com.share.myutils.download;

/**
 * Created by ToaHanDong on 2017/12/28.
 * 标示着 下载过程中的状态
 */

public class DownloadStatus {

    public static final int WAIT = 42;       //等待
    public static final int PREPARE = 43;    //准备
    public static final int LOADING = 44;    //下载中
    public static final int PAUSE = 45;      //暂停Todo
    public static final int COMPLETE = 46;   //完成
    public static final int FAIL = 47;       //失败
}
