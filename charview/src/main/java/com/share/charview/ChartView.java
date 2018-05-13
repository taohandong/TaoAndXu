package com.share.charview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.share.charview.draw.data.Chart;
import com.share.charview.draw.data.InputData;
import com.share.charview.utils.ValueUtils;

import java.util.ArrayList;
import java.util.List;

public class ChartView extends View implements ChartManager.AnimationListener {

    private ChartManager chartManager;
    private ChartManager chartManager1;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec) / 2;
        chartManager.chart().setWidth(width);
        chartManager.chart().setHeight(height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        chartManager.drawer().draw(canvas);
    }

    @Override
    public void onAnimationUpdated() {
        invalidate();
    }

    public void setData(@Nullable List<InputData> dataList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        final Chart chart = chartManager.chart();
        chart.setInputData(dataList);
        chartManager.drawer().updateTitleWidth();
        chartManager.drawer().setLineColor();
        post(new Runnable() {
            @Override
            public void run() {
                chart.setDrawData(ValueUtils.getDrawData(chart));
                chartManager.animate();
            }
        });
    }


    public void setData1(@Nullable List<InputData> dataList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        final Chart chart = chartManager1.chart();
        chart.setInputData(dataList);
        chartManager1.drawer().updateTitleWidth();
        chartManager1.drawer().setLineColor1();
        post(new Runnable() {
            @Override
            public void run() {
                chart.setDrawData(ValueUtils.getDrawData(chart));
                chartManager1.animate();
            }
        });
    }

    private void init() {
        chartManager = new ChartManager(getContext(), this);
        chartManager1 = new ChartManager(getContext(), this);
    }
}
