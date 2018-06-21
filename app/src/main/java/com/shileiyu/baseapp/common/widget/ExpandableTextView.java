package com.shileiyu.baseapp.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.shileiyu.baseapp.R;

/**
 * @author shilei.yu
 * @date 2018/6/21
 * @desc 可展开的TextView
 */

public class ExpandableTextView extends AppCompatTextView {

    /**
     * 是否处于展开状态
     */
    private boolean isExpand = false;
    /**
     * 折叠时需要展示几行
     */
    private int mLinesWhenCollapsed = 1;

    private OnExpandStateChangeListener mListener;


    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setExpand(isExpand);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        if (a != null) {
            isExpand = a.getBoolean(R.styleable.ExpandableTextView_expand, false);
            mLinesWhenCollapsed = a.getInt(R.styleable.ExpandableTextView_min_line, 1);
            a.recycle();
        }
    }

    public boolean setExpand(boolean expand) {
        if (getTotalLine() > mLinesWhenCollapsed) {
            if (expand) {
                setMaxLines(Integer.MAX_VALUE >> 2);
            } else {
                setMaxLines(mLinesWhenCollapsed);
            }
            isExpand = expand;
            notifyExpandStateChanged(isExpand);
        } else {
            notifyExpandStateChanged(true);
        }
        return false;
    }

    private int getTotalLine() {
        if (TextUtils.isEmpty(getText())) {
            return 0;
        }
        int innerWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        if (innerWidth > 0) {
            float textTotalWidth = getPaint().measureText(getText().toString());
            return (int) (textTotalWidth / innerWidth);
        }
        return 0;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        setExpand(isExpand);
    }

    public boolean isExpand() {
        return isExpand;
    }

    private void notifyExpandStateChanged(boolean expand) {
        if (mListener != null) {
            mListener.onExpand(expand);
        }
    }

    public void setListener(OnExpandStateChangeListener listener) {
        mListener = listener;
    }

    /**
     * TextView展开 or关闭 状态监听器
     */
    public interface OnExpandStateChangeListener {
        public void onExpand(boolean expand);
    }

}
