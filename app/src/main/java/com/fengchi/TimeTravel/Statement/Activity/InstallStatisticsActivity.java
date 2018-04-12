package com.fengchi.TimeTravel.Statement.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Statement.ChartUtils;
import com.fengchi.TimeTravel.Utils.StatusBarCompat;
import com.fengchi.TimeTravel.Utils.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstallStatisticsActivity extends Activity {
    @Bind(R.id.statement_titlebar)
    TitleBarView statementTitlebar;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_statistics);
        StatusBarCompat.compat(this, Color.parseColor("#FFFFFF"));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        chart = (LineChart) findViewById(R.id.chart);
        ChartUtils.initChart(chart);
        ChartUtils.notifyDataSetChanged(chart, getData(), ChartUtils.dayValue);
        ChartUtils.notifyDataSetChanged(chart, getData2(), ChartUtils.dayValue);
        statementTitlebar.setTitle("电梯安装数量统计");
    }

    private List<Entry> getData() {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 15));
        values.add(new Entry(1, 100));
        values.add(new Entry(2, 40));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 65));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 25));
        values.add(new Entry(7, 85));
        values.add(new Entry(8, 25));
        values.add(new Entry(9, 96));
        values.add(new Entry(10, 35));
        values.add(new Entry(11, 16));
        return values;
    }
    private List<Entry> getData2() {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 454));
        values.add(new Entry(1, 234));
        values.add(new Entry(2, 40));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 453));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 25));
        values.add(new Entry(7, 456));
        values.add(new Entry(8, 25));
        values.add(new Entry(9, 234));
        values.add(new Entry(10, 35));
        values.add(new Entry(11, 16));
        return values;
    }
}
