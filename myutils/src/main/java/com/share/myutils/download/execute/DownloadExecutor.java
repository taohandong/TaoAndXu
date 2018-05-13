package com.share.myutils.download.execute;

import android.content.Intent;

import com.share.myutils.download.DownloadConstant;
import com.share.myutils.download.DownloadStatus;
import com.share.myutils.download.utils.LogUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ToaHanDong on 2017/12/28.
 */

public class DownloadExecutor extends ThreadPoolExecutor {
    public static final String TAG = "DownloadExecutor";

    public DownloadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                            TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void executeTask(DownloadTask task){
        int status = task.getStatus();
        if (status== DownloadStatus.PAUSE || status== DownloadStatus.FAIL){
            task.setFileStatus(DownloadStatus.WAIT);

            Intent intent = new Intent();
            intent.setAction(task.getDownLoadInfo().getAction());
            intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, task.getFileInfo());
            task.sendBroadcast(intent);

            execute(task);
        }else {
            LogUtils.w(TAG, "文件状态不正确, 不进行下载 FileInfo=" + task.getFileInfo());
        }
    }
}
