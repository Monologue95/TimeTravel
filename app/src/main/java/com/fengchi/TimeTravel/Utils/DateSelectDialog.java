package com.fengchi.TimeTravel.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import com.fengchi.TimeTravel.R;

import java.util.Calendar;

public class DateSelectDialog extends Activity implements OnClickListener {
    public static final int REQUEST_CODE_DATE = 20;//请求日期获取的标记
    private Button mConfirmBtn;
    private TextView mBeginText, mEndText;

    private DatePicker mDateEnd, mDateBegin;
    private int mYearBegin, mMonthBegin, mDayBegin;

    private int nowYear, nowMonth, nowDay;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_order_manager_dateselect_activity);
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        nowYear = mYearBegin = c.get(Calendar.YEAR);
        nowMonth = mMonthBegin = c.get(Calendar.MONTH) + 1;
        nowDay = mDayBegin = c.get(Calendar.DAY_OF_MONTH);
        LogUtil.error("DateSelectDialog", "init:" + mYearBegin + "-" + mMonthBegin + "-"
                + mDayBegin);
        initView();
        initDate();
    }

    public void initView() {
        mBeginText = (TextView) findViewById(R.id.wb_order_manager_date_select_begin_txt);
        mEndText = (TextView) findViewById(R.id.wb_order_manager_date_select_end_txt);
        mConfirmBtn = (Button) findViewById(R.id.wb_order_manager_date_select_confirm_btn);
        mDateBegin = (DatePicker) findViewById(R.id.date_begin);
        mDateEnd = (DatePicker) findViewById(R.id.date_end);
        mConfirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mConfirmBtn) {
            if (DateUtils.getDateTrim(mYearBegin, mMonthBegin, mDayBegin) > DateUtils.getDateTrim(nowYear, nowMonth, nowDay)) {
                ToastUtils.showToast_Now(DateSelectDialog.this, "所选时间不可大于当前日期，请重新选择!");
            } else {
                Intent intent = new Intent();
                intent.putExtra("Date", mYearBegin + "-" + mMonthBegin + "-"
                        + mDayBegin);
                setResult(1, intent);
                finish();
            }
        }

    }

    public void initDate() {
        // 初始化DatePicker组件，初始化时指定监听器
        mDateBegin.init(mYearBegin, mMonthBegin - 1, mDayBegin, new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int month,
                                      int day) {
                if (DateUtils.getDateTrim(year, month + 1, day) > DateUtils.getDateTrim(nowYear, nowMonth, nowDay)) {
                    ToastUtils.showToast_Now(DateSelectDialog.this, "所选时间不可大于当前日期!");
                }
                mYearBegin = year;
                mMonthBegin = month + 1;
                mDayBegin = day;

                LogUtil.error("DateSelectDialog", "onDateChanged:" + mYearBegin + "-" + mMonthBegin + "-"
                        + mDayBegin);

                //mBeginText.setText(mYearBegin + "-" + mMonthBegin + "-"+ mDayBegin);
            }
        });

    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, DateSelectDialog.class);
        activity.startActivityForResult(intent, REQUEST_CODE_DATE);
    }
}
