package com.shileiyu.baseapp.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.shileiyu.baseapp.R;

/**
 * @author shilei.yu
 * @date 2018/7/5
 */

public class RoundConWebView extends WebView {
    private int roundRadius = 0;
    private Path roundPath = new Path();
    private RectF boundRectF = new RectF();
    private int mH;
    private int mW;

    public RoundConWebView(Context context) {
        this(context, null);
    }

    public RoundConWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundConWebView);
            roundRadius = ta.getInt(R.styleable.RoundConWebView_round_radius, 0);
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mH = getMeasuredHeight();
        mW = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int maxRadius = Math.min(mH, mW) / 2;
        if (roundRadius > 0 && roundRadius < maxRadius) {
            roundPath.reset();
            int scrollX = getScrollX();

            int scrollY = getScrollY();

            boundRectF.set(scrollX, scrollY, scrollX + mW, scrollY + mH);
            roundPath.addRoundRect(boundRectF, roundRadius, roundRadius, Path.Direction.CW);
            canvas.clipPath(roundPath);
        }

        super.onDraw(canvas);
    }
}
