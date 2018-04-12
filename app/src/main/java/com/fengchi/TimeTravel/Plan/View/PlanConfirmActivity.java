package com.fengchi.TimeTravel.Plan.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Utils.DateUtils;
import com.fengchi.TimeTravel.Utils.LogUtil;
import com.fengchi.TimeTravel.Utils.SnackBarUtil;
import com.fengchi.TimeTravel.Utils.TimeSelector.DateListener;
import com.fengchi.TimeTravel.Utils.TimeSelector.TimeConfig;
import com.fengchi.TimeTravel.Utils.TimeSelector.TimeSelectorDialog;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanConfirmActivity extends AppCompatActivity {
    @Bind(R.id.tv_begin)
    TextView tvBegin;
    @Bind(R.id.tv_complete)
    TextView tvComplete;
    @Bind(R.id.activity_plan_confirm)
    RelativeLayout activityPlanConfirm;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_confirm);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.tv_begin, R.id.tv_complete})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tv_begin:
                final TimeSelectorDialog dialog = new TimeSelectorDialog(this);
                //设置标题
                dialog.setTimeTitle("选择时间:");
                //显示类型
                dialog.setIsShowtype(TimeConfig.YEAR_MONTH_DAY);
                //默认时间
                dialog.setCurrentDate(DateUtils.format.format(new Date()));
                LogUtil.error("isbigger", DateUtils.isDateOneBigger(DateUtils.format_Y_M_D_H_m.format(new Date()), "2017-09-26") + "");
                DateUtils.isDateOneBigger(DateUtils.format_Y_M_D_H_m.format(new Date()), "2017-09-26");
                DateUtils.getGapCount(DateUtils.format_Y_M_D_H_m.format(new Date()), "2017-09-26");
                LogUtil.error("getGapCount", DateUtils.getGapCount(DateUtils.format_Y_M_D_H_m.format(new Date()), "2017-09-26") + "");
                //隐藏清除按钮
                dialog.setEmptyIsShow(false);
                dialog.setDateListener(new DateListener() {
                    @Override
                    public void onReturnDate(String time, int year, int month, int day, int hour, int minute, int isShowType) {
                        SnackBarUtil.showSnackBarAction(view, time, "更改时间", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.show();
                            }
                        });
                    }

                    @Override
                    public void onReturnDate(String empty) {
                        Toast.makeText(mContext, empty, Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
                break;
            case R.id.tv_complete:
                break;
        }
    }
}
