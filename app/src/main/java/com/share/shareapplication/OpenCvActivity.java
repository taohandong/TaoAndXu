package com.share.shareapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.share.myutils.opencv.android.CameraBridgeViewBase;
import com.share.myutils.opencv.android.OpenCVLoader;
import com.share.myutils.opencv.core.CvType;
import com.share.myutils.opencv.core.Mat;

import butterknife.BindView;

public class OpenCvActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    static {
        System.loadLibrary("native-math");
        System.loadLibrary("native-opencv");
    }

    private native int addFromCpp(int a, int b);

    private native void nativeProcessFrame(long addrGray, long addrRGBA);


    public static void getInstance(Context context) {
        Intent intent = new Intent(context, OpenCvActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.javaCameraView)
    CameraBridgeViewBase javaCameraView;

    @BindView(R.id.tvShow)
    TextView tvShow;

    private Mat rgba, gray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cv);
        tvShow.setText(addFromCpp(3, 5) + "");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        javaCameraView.setVisibility(View.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        rgba = new Mat(height, width, CvType.CV_8UC4);
        gray = new Mat(height, width, CvType.CV_8UC1);
    }

    @Override
    public void onCameraViewStopped() {
        rgba.release();
        gray.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        rgba = inputFrame.rgba();
        gray = inputFrame.gray();
        nativeProcessFrame(gray.getNativeObjAddr(), rgba.getNativeObjAddr());
        return rgba;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null!=javaCameraView)javaCameraView.disableView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug())javaCameraView.enableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=javaCameraView)javaCameraView.disableView();
    }
}
