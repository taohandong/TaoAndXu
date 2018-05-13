package com.share.mvpsdk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ToaHanDong on 2018/2/7.
 */

public class MyEyeView extends View {

    Paint myPaint;

    int mypaintWidth=2;

    int centerLeft=0,centerTop=0,centerRight=0,centerBottom=0;
    public MyEyeView(Context context) {
        super(context);
        init(context);
    }

    public MyEyeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyEyeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        while (centerLeft>0){
            centerLeft-=10;
            centerTop-=10;
            centerRight+=10;
            centerBottom+=10;
            canvas.drawRect(centerLeft,centerTop,centerRight,centerBottom,myPaint);
        }
    }

    private void init(Context context) {
        myPaint=new Paint();
        myPaint.setColor(Color.WHITE);
        myPaint.setAntiAlias(true);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(mypaintWidth);
//        myPaint.setShader(new Shader());
    }

    public void setMyPaintWidth(int paintWidth){
        mypaintWidth=paintWidth;
    }

    public void setIndexPoint(int centerLeft,int centerTop,int centerRight,int centerBottom){
        this.centerLeft=centerLeft;
        this.centerTop=centerTop;
        this.centerRight=centerRight;
        this.centerBottom=centerBottom;
        invalidate();
    }

}
