package com.shileiyu.baseapp.common.widget.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author shilei.yu
 * @since on 2018/3/29.
 */

public class MonthView extends ViewGroup {
    private CalendarDay firstDay;
    private int dayOfCount = 20;

    public MonthView(Context context) {
        super(context);
        setUpChild(context);
    }

    private void setUpChild(Context context) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
