package com.share.mvpsdk.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.share.mvpsdk.utils.FileUtils;
import com.share.mvpsdk.utils.NetworkConnectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ToaHanDong on 2018/1/25.
 */

public class MyViewView extends WebView {

    public MyViewView(Context context) {
        super(context);
        init(context);
    }

    public MyViewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyViewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private String TAG="MyWebView";
    //WebView的设置类
    private WebSettings webSettings = null;

    private void init(Context context) {

        initWebSetting(context);

    }

    private void initWebSetting(Context context) {

        webSettings = getSettings();//初始化websettings

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);//将图片调整适合WebView的大小
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(true);//隐藏原生的缩放控件
        //其他细节操作
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true);//设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新窗口
        webSettings.setLoadsImagesAutomatically(true);//设置自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式为ytf-8
        if (NetworkConnectionUtils.isConnected(context))
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        else webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        if (!FileUtils.webViewCacheIsExit()) {
            webSettings.setAppCachePath(FileUtils.getCache()); //设置  Application Caches 缓存目录,只能设置一次
            webSettings.setAppCacheMaxSize(8 * 1024 * 1024);
        }
    }

    FrameLayout frame;
    public void setView(FrameLayout frame) {
        this.frame=frame;
    }

    /**
     * js接口
     */
    public class SupportJavascriptInterface {
        private Context context;

        public SupportJavascriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(final String img) {
            Log.d(TAG,"openImage="+img);
//            goBackOrForward(-1);
//            AppUtils.runOnUIThread(new Runnable() {
//                @Override
//                public void run() {
//                    gotoImageBrowse(img);
//                }
//            });
        }
    }

    //    WebViewClient类（主要作用是：处理各种通知 & 请求事件）
    public void setMyWebViewClient(String url) {
        //步骤1： 选择加载方式
//方式a. 加载一个网页：
        loadUrl(url);
//方式b：加载apk包中的html页面
//        loadUrl("file:///android_asset/test.html");
//方式c：加载手机本地的html页面
//        loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
        addJavascriptInterface(new SupportJavascriptInterface(getContext()),"imagelistener");
        setWebViewClient(new WebViewClient() {

            //设定加载开始的操作
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG,"onPageStarted="+url+"favicon="+favicon);
            }

            //html加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG,"onPageFinished="+url);
                addWebImageClickListner(view);
            }

            // 注入js函数监听
            protected void addWebImageClickListner(WebView webView) {
                // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，
                // 函数的功能是在图片点击的时候调用本地java接口并传递url过去
                webView.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "    objs[i].onclick=function()  " +
                        "    {  "
                        + "        window.imagelistener.openImage(this.src);  " +
                        "    }  " +
                        "}" +
                        "})()");
            }

            /**
             * 加载资源时会调用该方法
             * @param view
             * @param url
             */
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d(TAG,"onLoadResource="+url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                switch (errorCode) {
                    case WebViewClient.ERROR_BAD_URL:
                        Log.d(TAG,"ERROR_BAD_URL");
                        break;
                    case WebViewClient.ERROR_UNKNOWN:
                        Log.d(TAG,"ERROR_UNKNOWN");
                        break;
                }
            }

//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    switch (error.getErrorCode())
//                }
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.d("66666","url="+url);
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    getContext().startActivity(intent);
                    return true;
                } else {
                    Map<String, String> extraHeaders = new HashMap<String, String>();
                    extraHeaders.put("Referer", "http://wxpay.wxutil.com");
                    view.loadUrl(url, extraHeaders);
                }
//                view.loadUrl(url);
                return true;
            }

            //webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.d(TAG,"onReceivedSslError");
                handler.proceed();//表示等待证书响应
            }
        });

    }

    //辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
    public void setMyWebChromeClient(){
        setWebChromeClient(new WebChromeClient(){

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                Log.d(TAG,"onShowCustomView=");
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG,"newProgress="+newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d(TAG,"onReceivedTitle="+title);
            }

            /**
             * js中的弹出框
             * @param view
             * @param url
             * @param message
             * @param result 可以对弹出框做取消和确认操作
             * @return
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG,"onJsAlert="+message+"url="+url);
                return super.onJsAlert(view, url, message, result);
            }

//            支持javascript输入框,点击确认返回输入框中的值，点击取消返回 null。
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
    }


    //WebView的状态

    /**
     * 激活webView为活跃状态，能正常执行网页的响应
     */
    public void setonResume() {
        onResume();
    }

    /**
     * 当页面被失去焦点被切换到后台不可见状态，需要执行onPause
     * 通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
     */
    public void setOnPause() {
        onPause();
    }

    /**
     * 当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
     * 它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
     */
    public void setPauseTimers() {
        pauseTimers();
    }

    /**
     * 恢复pauseTimers状态
     */
    public void setResumeTimers() {
        resumeTimers();
    }

    public void destroyWebView() {
        removeView(this);
        destroy();
    }

    //WebView的简单操作

    /**
     * 判断网页是否可以回退
     *
     * @return
     */
    public boolean getCanGoBack() {
        return canGoBack();
    }

    /**
     * 回退网页
     */
    public void setGoBack() {
        goBack();
    }

    /**
     * 设置是否可以前进
     *
     * @return
     */
    public boolean setCanForward() {
        return canGoForward();
    }

    /**
     * 前进网页
     */
    public void setGoForward() {
        goForward();
    }

    /**
     * 以当前的index为起始点前进或者后退到历史记录中指定的steps
     * 如果steps为负数则为后退，正数则为前进
     *
     * @param steps
     */
    public void setGoBackOrForward(int steps) {
        goBackOrForward(steps);
    }

    //清除缓存数据

    /**
     * 清除网页访问留下的缓存
     * 由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
     *
     * @param isClear
     */
    public void clearMyCache(boolean isClear) {
        clearCache(isClear);
    }

    /**
     * 清除当前webview访问的历史记录
     * 只会webview访问历史记录里的所有记录除了当前访问记录
     */
    public void clearMyHistory() {

    }

    /**
     * 这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
     */
    public void clearMyFormData() {
        clearFormData();
    }


}
