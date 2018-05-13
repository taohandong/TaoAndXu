package com.share.charview.draw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.Log;

import com.share.charview.R;
import com.share.charview.animation.AnimationManager;
import com.share.charview.animation.data.AnimationValue;
import com.share.charview.draw.data.Chart;
import com.share.charview.draw.data.DrawData;
import com.share.charview.draw.data.InputData;
import com.share.charview.utils.DateUtils;
import com.share.charview.utils.ValueUtils;

import java.util.ArrayList;
import java.util.List;

public class DrawController {

    private Context context;
    private Chart chart;
    private AnimationValue value;

    private Paint frameLinePaint;
    private Paint frameInternalPaint;
    private Paint frameTextPaint;

    private Paint linePaint;
    private Paint strokePaint;
    private Paint fillPaint;

    private Path path = null;//填充颜色路径

    public DrawController(@NonNull Context context, @NonNull Chart chart) {
        this.context = context;
        this.chart = chart;
        init();
    }

    public void updateTitleWidth() {
        int titleWidth = getTitleWidth();
        chart.setTitleWidth(titleWidth);
    }

    public void updateValue(@NonNull AnimationValue value) {
        this.value = value;
    }

    public void draw(@NonNull Canvas canvas) {
        drawFrame(canvas);
        drawChart(canvas);
    }

    private void drawFrame(@NonNull Canvas canvas) {
        drawChartVertical(canvas);
        drawChartHorizontal(canvas);
        drawFrameLines(canvas);
    }

    private void drawChartVertical(@NonNull Canvas canvas) {
        List<InputData> inputDataList = chart.getInputData();
        if (inputDataList == null || inputDataList.isEmpty()) {
            return;
        }

        int maxValue = ValueUtils.max(inputDataList);
        int correctedMaxValue = ValueUtils.getCorrectedMaxValue(maxValue);
        float value = (float) correctedMaxValue / maxValue;

        int heightOffset = chart.getHeightOffset();
        int padding = chart.getPadding();
        int textSize = chart.getTextSize();
        int titleWidth = chart.getTitleWidth();

        float width = chart.getWidth() - chart.getTextSize() - chart.getPadding();
        float height = chart.getHeight() - textSize - padding;
        float chartPartHeight = ((height - heightOffset) * value) / Chart.CHART_PARTS;

        float currHeight = height;
        int currTitle = 0;

        for (int i = 0; i <= Chart.CHART_PARTS; i++) {
            float titleY = currHeight;

            if (i <= 0) {
                titleY = height;

            } else if (textSize + chart.getHeightOffset() > currHeight) {
                titleY = currHeight + textSize - Chart.TEXT_SIZE_OFFSET;
            }

            if (i > 0) {
//                canvas.drawLine(titleWidth, currHeight, width, currHeight, frameInternalPaint);
            }

            String title = String.valueOf(currTitle);
            canvas.drawText(title, padding, titleY, frameTextPaint);

            currHeight -= chartPartHeight;
            currTitle += correctedMaxValue / Chart.CHART_PARTS;
        }
    }

    private void drawChartHorizontal(@NonNull Canvas canvas) {
        List<InputData> inputDataList = chart.getInputData();
        List<DrawData> drawDataList = chart.getDrawData();

        if (inputDataList == null || inputDataList.isEmpty() || drawDataList == null || drawDataList.isEmpty()) {
            return;
        }
//        drawPath(drawDataList,canvas);
        for (int i = 0; i < inputDataList.size(); i++) {

            InputData inputData = inputDataList.get(i);
            String date = DateUtils.format(inputData.getMillis());
            int dateWidth = (int) frameTextPaint.measureText(date);
            int x;
            if (drawDataList.size() > i) {
                DrawData drawData = drawDataList.get(i);
                x = drawData.getStartX();
                if (i > 0) {
//					x -= (dateWidth / 2);
                    x = drawDataList.get(i).getStartX() - (dateWidth / 2) - chart.getPadding() - chart.getTextSize();
                }
            } else {
                x = drawDataList.get(drawDataList.size() - 1).getStopX() - dateWidth - chart.getPadding() - chart.getTextSize();
            }
            if (i > 0)
                canvas.drawLine(i == drawDataList.size() ? x + chart.getTextSize() : x + dateWidth / 2, drawDataList.get(i - 1).getStopY(), i == drawDataList.size() ? x + chart.getTextSize() : x + dateWidth / 2, chart.getHeight() - chart.getTextSize() - chart.getPadding(), frameInternalPaint);
            canvas.drawText(date, x, chart.getHeight(), frameTextPaint);
        }
    }

    //画路径
    private void drawPath(List<DrawData> drawDataList, Canvas canvas) {
        for (int i = 0; i < drawDataList.size(); i++) {
            DrawData drawData = drawDataList.get(i);
            path.moveTo(drawData.getStartX(), drawData.getStopY());
            if (i < drawDataList.size() - 1) {
                DrawData drawData1 = drawDataList.get(i + 1);
                path.lineTo(drawData1.getStartX(), drawData1.getStopY());
                path.lineTo(drawData1.getStartX(), chart.getHeight() - chart.getPadding());
            }
            path.lineTo(drawData.getStartX(), chart.getHeight() - chart.getPadding());
            path.close();
        }
        canvas.drawPath(path, linePaint);
    }

    private void drawFrameLines(@NonNull Canvas canvas) {
        int textSize = chart.getTextSize();
        int padding = chart.getPadding();

        int height = chart.getHeight() - textSize - padding;
        int width = chart.getWidth() - textSize - padding;
        int titleWidth = chart.getTitleWidth();
        int heightOffset = chart.getHeightOffset();

        canvas.drawLine(titleWidth, heightOffset, titleWidth, height, frameLinePaint);
        canvas.drawLine(titleWidth, height, width, height, frameLinePaint);
    }

    private void drawChart(@NonNull Canvas canvas) {
        int runningAnimationPosition = value != null ? value.getRunningAnimationPosition() : AnimationManager.VALUE_NONE;
//        Log.d("66666","runningAnimationPosition="+runningAnimationPosition);
        for (int j = 0; j < 2; j++) {

            for (int i = 0; i < runningAnimationPosition; i++) {
                drawChart(canvas, i, false, j);
            }

            if (runningAnimationPosition > AnimationManager.VALUE_NONE) {
                drawChart(canvas, runningAnimationPosition, true, j);
            }
        }
    }

    private void drawChart(@NonNull Canvas canvas, int position, boolean isAnimation, int j) {
//        List<DrawData> dataList = chart.getDrawData();
        List<DrawData> dataList = new ArrayList<>();
        if (j == 0) {
            DrawData drawData = new DrawData();
            drawData.setStartX(828);
            drawData.setStartY(1029);
            drawData.setStopX(1033);
            drawData.setStopY(940);
            dataList.add(drawData);

            DrawData drawData1 = new DrawData();
            drawData1.setStartX(1023);
            drawData1.setStartY(930);
            drawData1.setStopX(1228);
            drawData1.setStopY(960);
            dataList.add(drawData1);


        } else {
            DrawData drawData = new DrawData();
            drawData.setStartX(308);
            drawData.setStartY(509);
            drawData.setStopX(513);
            drawData.setStopY(420);
            dataList.add(drawData);

            DrawData drawData1 = new DrawData();
            drawData1.setStartX(503);
            drawData1.setStartY(410);
            drawData1.setStopX(708);
            drawData1.setStopY(440);
            dataList.add(drawData1);
        }
        if (dataList == null || position > dataList.size() - 1) {
            return;
        }

        DrawData drawData = dataList.get(position);
        int startX = drawData.getStartX();
        int startY = drawData.getStartY();

        int stopX;
        int stopY;
        int alpha;

        if (isAnimation) {
            stopX = value.getX();
            stopY = value.getY();
            alpha = value.getAlpha();

        } else {
            stopX = drawData.getStopX();
            stopY = drawData.getStopY();
            alpha = AnimationManager.ALPHA_END;
        }

        drawChart(canvas, startX, startY, stopX, stopY, alpha, position);
    }

    private void drawChart(@NonNull Canvas canvas, int startX, int startY, int stopX, int stopY, int alpha, int position) {
        int radius = chart.getRadius();
        int inerRadius = chart.getInerRadius();
        if (position > 0)
            canvas.drawLine(startX - chart.getTextSize() - chart.getPadding(), startY, stopX - chart.getTextSize() - chart.getPadding(), stopY, linePaint);
        else
            canvas.drawLine(startX, startY, stopX - chart.getPadding() - chart.getTextSize(), stopY, linePaint);

        if (position > 0) {
            strokePaint.setAlpha(alpha);
            canvas.drawCircle(startX - chart.getPadding() - chart.getTextSize(), startY, radius, strokePaint);
            canvas.drawCircle(startX - chart.getTextSize() - chart.getPadding(), startY, inerRadius, fillPaint);
        }
    }

    private int getTitleWidth() {
        List<InputData> valueList = chart.getInputData();
        if (valueList == null || valueList.isEmpty()) {
            return 0;
        }

        String maxValue = String.valueOf(ValueUtils.max(valueList));
        int titleWidth = (int) frameTextPaint.measureText(maxValue);
        int padding = chart.getPadding();

        return padding + titleWidth + padding;
    }

    public void setLinColor() {
        linePaint.setColor(context.getResources().getColor(R.color.gray_900));
    }

    private void init() {
        path = new Path();
        Resources res = context.getResources();
        chart.setHeightOffset((int) (res.getDimension(R.dimen.radius) + res.getDimension(R.dimen.line_width)));
        chart.setPadding((int) res.getDimension(R.dimen.frame_padding));
        chart.setTextSize((int) res.getDimension(R.dimen.frame_text_size));
        chart.setRadius((int) res.getDimension(R.dimen.radius));
        chart.setInerRadius((int) res.getDimension(R.dimen.iner_radius));

        //轴线的颜色
        frameLinePaint = new Paint();
        frameLinePaint.setAntiAlias(true);
        frameLinePaint.setStrokeWidth(res.getDimension(R.dimen.frame_line_width));
        frameLinePaint.setColor(res.getColor(R.color.light_blue));

        frameInternalPaint = new Paint();
        frameInternalPaint.setAntiAlias(true);
        frameInternalPaint.setStrokeWidth(res.getDimension(R.dimen.frame_line_width));
        frameInternalPaint.setColor(res.getColor(R.color.blue));

        frameTextPaint = new Paint();
        frameTextPaint.setAntiAlias(true);
        frameTextPaint.setTextSize(chart.getTextSize());
        frameTextPaint.setColor(res.getColor(R.color.gray_400));

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(res.getDimension(R.dimen.line_width));
        linePaint.setColor(res.getColor(R.color.blue));

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);
        strokePaint.setStrokeWidth(res.getDimension(R.dimen.line_width));
        strokePaint.setColor(res.getColor(R.color.blue));

        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(res.getColor(R.color.white));
    }

    public void setLineColor1() {
        linePaint.setColor(context.getResources().getColor(R.color.blue));
    }
}
