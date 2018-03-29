package com.shileiyu.baseapp.ui.calendar;


import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


public class CalendarActivity extends BaseActivity {


    @BindView(R.id.act_calendar_material)
    MaterialCalendarView mCalendar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    private CalendarDay first;
    private CalendarDay second;

    @Override
    protected void initView() {
        mCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (selected) {
                    if (first == null) {
                        first = date;
                    } else {
                        if (first.isBefore(date)) {
                            second = date;
                        } else {
                            first = date;
                            second = null;
                        }
                    }
                }
                if (first != null && second != null) {
                    mCalendar.selectRange(first, second);
                }
                showToast(selected + " " + date.toString());
                Logger.d("onDateSelected = " + date);
            }
        });
        mCalendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                Logger.d("onRangeSelected = " + dates);
            }
        });
    }

}
