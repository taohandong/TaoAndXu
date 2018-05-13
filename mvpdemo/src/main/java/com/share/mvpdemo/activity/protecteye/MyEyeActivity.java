package com.share.mvpdemo.activity.protecteye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.share.mvpdemo.R;
import com.share.mvpsdk.utils.DisplayUtils;
import com.share.mvpsdk.view.MyEyeView;

public class MyEyeActivity extends AppCompatActivity {

    FrameLayout frame_parent;

    MyEyeView myEyeView=null;

    private int screenWidth=0,screenHeight=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_eye);
        frame_parent=findViewById(R.id.frame_parent);
        myEyeView=new MyEyeView(this);
        frame_parent.addView(myEyeView);
        screenWidth= DisplayUtils.getScreenWidthPixels(this)/2;
        screenHeight=DisplayUtils.getScreenHeightPixels(this)/2;
        myEyeView.setIndexPoint(screenWidth-5,screenWidth-5,screenHeight+5,screenHeight+5);
    }
}
