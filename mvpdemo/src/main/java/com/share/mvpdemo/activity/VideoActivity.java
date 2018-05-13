package com.share.mvpdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.share.mvpdemo.R;

import java.io.File;

import cn.jzvd.JZVideoPlayerStandard;

public class VideoActivity extends AppCompatActivity {

    JZVideoPlayerStandard videopalyer;
    String videoPath= Environment.getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_DOWNLOADS+"/1.mp4";

    public static void getInstance(Context context){
        Intent intent=new Intent(context,VideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videopalyer=findViewById(R.id.videopalyer);
//        Log.d("66666","file="+new File(videoPath).exists());
        videopalyer.setUp(videoPath,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"视频");
//        videopalyer.onStatePlaying();
//        videopalyer.onStateAutoComplete();
        videopalyer.startVideo();
    }
}
