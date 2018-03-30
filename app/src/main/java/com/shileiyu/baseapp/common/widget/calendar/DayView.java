package com.shileiyu.baseapp.common.widget.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author shilei.yu
 * @since on 2018/3/29.
 */

public abstract class DayView<T extends CalendarDay> extends FrameLayout {
    private SparseArray<View> mViews = new SparseArray<>();
    private View content;

    public DayView(@NonNull Context context) {
        this(context, null);
    }

    public DayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        content = LayoutInflater.from(context).inflate(layoutId(), this, false);
        addView(content);
    }

    public abstract int layoutId();

    public abstract void bind(T day);

    @SuppressWarnings("unchecked")
    public <E extends View> E findView(int rid) {
        View child = mViews.get(rid);
        if (child == null) {
            child = findViewById(rid);
            if (child != null) {
                mViews.put(rid, child);
            }
        }
        return (E) child;
    }
}
