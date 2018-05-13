package com.share.shareapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.share.myutils.download.DownloadConstant;
import com.share.myutils.download.DownloadHelper;
import com.share.myutils.download.DownloadStatus;
import com.share.myutils.download.FileInfo;
import com.share.myutils.download.db.DbOpenHelper;
import com.share.myutils.download.utils.DebugUtils;
import com.share.myutils.utils.Utils_Parse;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String START = "开始";
    private static final String PAUST = "暂停";
    private final String ACTION = "download_first";

    private static final String firstUrl = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";

    TextView tvStart;

    ProgressBar progressBar;

    Button btnStart,btnDelete,btnOpenCv;

    DownloadHelper downloadHelper=null;

    File saveFile= new File(Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOWNLOADS,"测试.apk");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStart=findViewById(R.id.tvStart);
        progressBar=findViewById(R.id.progressBar);
        btnStart=findViewById(R.id.btnStart);
        btnDelete=findViewById(R.id.btnDelete);
        btnOpenCv=findViewById(R.id.btnOpenCv);
        downloadHelper=DownloadHelper.getInstance();
        initBroast();
        btnStart.setText(START);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnStart.getText().equals(START)){
                    downloadHelper.addTask(firstUrl, saveFile, ACTION).submit(MainActivity.this);
                    btnStart.setText(PAUST);
                }else {
                    downloadHelper.pauseTask(firstUrl,saveFile,ACTION).submit(MainActivity.this);
                    btnStart.setText(START);
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saveFile!=null&&saveFile.exists())saveFile.delete();
            }
        });

        btnOpenCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCvActivity.getInstance(MainActivity.this);
            }
        });
    }

    private void initBroast() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                switch (intent.getAction()) {
                    case ACTION://获取下载的文件信息
                        FileInfo fileInfo= (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
                        updateTextview(tvStart, progressBar, fileInfo, "汇作业", btnStart);
                        break;
                }
            }
        }
    };

    private void updateTextview(TextView textView, ProgressBar progressBar, FileInfo fileInfo, String fileName, Button btn){

        float pro = (float) (fileInfo.getDownloadLocation()*1.0/ fileInfo.getSize());
        int progress = (int)(pro*100);
        float downSize = fileInfo.getDownloadLocation() / 1024.0f / 1024;
        float totalSize = fileInfo.getSize()  / 1024.0f / 1024;

        //设置字体颜色
        int count = 0;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(fileName);
        sb.setSpan(new ForegroundColorSpan(Color.BLACK),0,sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count=sb.length();
        sb.append("\t  ( " + progress + "% )" + "\n");
        sb.setSpan(new ForegroundColorSpan(Color.BLUE), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();
        sb.append("状态:");
        sb.setSpan(new ForegroundColorSpan(Color.RED), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();

        sb.append(DebugUtils.getStatusDesc(fileInfo.getDownloadStatus()) + " \t \t\t \t\t\t");
        sb.setSpan(new ForegroundColorSpan(Color.GREEN), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();

        sb.append(Utils_Parse.getTwoDecimalsStr(downSize) + "M/" + Utils_Parse.getTwoDecimalsStr(totalSize) + "M\n");
        sb.setSpan(new ForegroundColorSpan(Color.RED), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(sb);


        progressBar.setProgress(progress);

        if (fileInfo.getDownloadStatus() == DownloadStatus.COMPLETE){
            btn.setText("下载完成");
            btn.setBackgroundColor(0xff5c0d);
        }
    }
}
