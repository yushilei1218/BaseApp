package com.shileiyu.baseapp.common.pattern;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author shilei.yu
 * @since on 2018/3/24.
 */

public class BridgePattern {
    public static abstract class AdapterView extends ViewGroup {
        protected BaseAdapter mAdapter;

        public AdapterView(Context context) {
            super(context);
        }

        public AdapterView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //测量所有child 蓝后设置自己的高宽
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            layoutChidren();
        }

        public void setAdapter(BaseAdapter adapter) {
            mAdapter = adapter;
        }

        public abstract void layoutChidren();
    }

    public static abstract class BaseAdapter {

        public abstract int getCount();

        public abstract View getView();
    }
}
