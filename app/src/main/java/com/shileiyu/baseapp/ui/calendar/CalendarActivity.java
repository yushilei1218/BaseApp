package com.shileiyu.baseapp.ui.calendar;


import android.support.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;

import butterknife.BindView;


public class CalendarActivity extends BaseActivity {


    @BindView(R.id.act_calendar_material)
    MaterialCalendarView mCalendar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initView() {
        mCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                showToast(selected + " " + date.toString());
            }
        });
    }

}
