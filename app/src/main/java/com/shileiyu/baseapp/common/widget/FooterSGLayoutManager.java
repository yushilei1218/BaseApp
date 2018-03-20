package com.shileiyu.baseapp.common.widget;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public class FooterSGLayoutManager extends StaggeredGridLayoutManager {
    public FooterSGLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FooterSGLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }
}
