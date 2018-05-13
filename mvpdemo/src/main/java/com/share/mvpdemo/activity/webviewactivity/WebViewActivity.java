package com.share.mvpdemo.activity.webviewactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.share.mvpdemo.R;
import com.share.mvpsdk.view.MyViewView;

public class WebViewActivity extends AppCompatActivity {

    MyViewView myWebView;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        myWebView=findViewById(R.id.myWebView);
        frame=findViewById(R.id.frame);
        Log.d("MyWebView","WebViewActivity=");
        myWebView.setMyWebViewClient("http://wxpay.wxutil.com/mch/pay/h5.v2.php");
        myWebView.setMyWebChromeClient();
        myWebView.setView(frame);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (myWebView.canGoBack())myWebView.setGoBack();
            else finish();
        }

        return true;
    }
}
