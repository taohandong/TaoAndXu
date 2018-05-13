package com.share.mvpdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.share.charview.ChartView;
import com.share.charview.draw.data.InputData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChartViewActivity extends AppCompatActivity {

    ChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        chartView=findViewById(R.id.chartView);
        chartView.setData(createChartData());
        chartView.setData1(createChartData1());
//        Log.d("66666","chart.getDrawData()="+createChartData1());
    }

    private List<InputData> createChartData1() {

        List<InputData> dataList = new ArrayList<>();
        dataList.add(new InputData(15));
        dataList.add(new InputData(24));
        dataList.add(new InputData(50));
        dataList.add(new InputData(30));
        dataList.add(new InputData(40));
        dataList.add(new InputData(80));
        dataList.add(new InputData(70));

        long currMillis = System.currentTimeMillis();
        currMillis -= currMillis % TimeUnit.DAYS.toMillis(1);

        for (int i = 0; i < dataList.size(); i++) {
            long position = dataList.size() - 1 - i;
            long offsetMillis = TimeUnit.DAYS.toMillis(position);

            long millis = currMillis - offsetMillis;
            dataList.get(i).setMillis(millis);
        }

        return dataList;

    }

    @NonNull
    private List<InputData> createChartData() {
        List<InputData> dataList = new ArrayList<>();
        dataList.add(new InputData(10));
        dataList.add(new InputData(25));
        dataList.add(new InputData(20));
        dataList.add(new InputData(30));
        dataList.add(new InputData(20));
        dataList.add(new InputData(50));
        dataList.add(new InputData(40));

        long currMillis = System.currentTimeMillis();
        currMillis -= currMillis % TimeUnit.DAYS.toMillis(1);

        for (int i = 0; i < dataList.size(); i++) {
            long position = dataList.size() - 1 - i;
            long offsetMillis = TimeUnit.DAYS.toMillis(position);

            long millis = currMillis - offsetMillis;
            dataList.get(i).setMillis(millis);
        }

        return dataList;
    }
}
