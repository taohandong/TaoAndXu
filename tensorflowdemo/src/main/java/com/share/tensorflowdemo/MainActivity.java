package com.share.tensorflowdemo;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.share.mvpsdk.utils.PermissionUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import cn.jzvd.JZVideoPlayerStandard;

public class MainActivity extends AppCompatActivity implements PermissionUtils.PermissionGrant{

    JZVideoPlayerStandard jzVideoPlayerStandard=null;
    MediaPlayer mediaPlayer=null;
    TextView tvStart;
    String mp3Path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+Environment.DIRECTORY_DOWNLOADS+"/jingdi1.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer=new MediaPlayer();
        try {
//            Log.d("66666",new File(mp3Path).exists()+"");
            mediaPlayer.setDataSource(mp3Path);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jzVideoPlayerStandard=new JZVideoPlayerStandard(this);
        tvStart=findViewById(R.id.tvStart);
        AssetManager assetManager = getAssets();
        PermissionUtils.requestMultiPermissions(this,this);
//        try {
//            AssetFileDescriptor afd = assetManager.openFd("raw/jingdi.mp3");
//            FileDescriptor fd = afd.getFileDescriptor();
//            mediaPlayer.setDataSource(fd,afd.getStartOffset(),afd.getLength());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mediaPlayer.isPlaying()){
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                }else {
//                    mediaPlayer.start();
//                }
            }
        });
    }

    @Override
    public void onPermissionGranted(int requestCode) {

    }
}
