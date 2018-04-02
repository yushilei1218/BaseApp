package com.shileiyu.baseapp.ui.lagou.behavior;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author shilei.yu
 * @since on 2018/4/2.
 */

public class HeadBarLayout extends LinearLayout {
    public HeadBarLayout(Context context) {
        this(context, null);
    }

    public HeadBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
    }

    public static class Behavior extends CoordinatorLayout.Behavior<HeadBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, HeadBarLayout child, View target, int dx, int dy, int[] consumed) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        }
    }
}
