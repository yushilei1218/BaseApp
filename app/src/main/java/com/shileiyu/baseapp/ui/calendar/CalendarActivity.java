package com.shileiyu.baseapp.ui.calendar;


import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.widget.PathTextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import yanzhikai.textpath.AsyncTextPathView;
import yanzhikai.textpath.SyncTextPathView;


public class CalendarActivity extends BaseActivity {


    @BindView(R.id.act_calendar_material)
    MaterialCalendarView mCalendar;
    @BindView(R.id.path_view)
    PathTextView pathView;
    @BindView(R.id.async_path_view)
    AsyncTextPathView apathView;
    @BindView(R.id.sync_path_view)
    SyncTextPathView spathView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    private CalendarDay first;
    private CalendarDay second;

    @Override
    protected void initView() {
        apathView.startAnimation(0,1);
        spathView.startAnimation(1,0);
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
