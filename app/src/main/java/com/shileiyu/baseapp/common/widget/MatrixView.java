package com.shileiyu.baseapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author shilei.yu
 * @since on 2018/5/3.
 */
public class MatrixView extends View {
    public static final int offset = 100;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Matrix mMatrix = new Matrix();

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2f);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(mMatrix);
        int count = getWidth() / offset;
        for (int j = 1; j <= count; j++) {
            int x = j * offset;
            canvas.drawLine(x, 0, x, getHeight(), mPaint);
        }
        count = getHeight() / offset;
        for (int i = 1; i <= count; i++) {
            int y = i * offset;
            canvas.drawLine(0, y, getWidth(), y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float cX = getWidth() / 2f;
                float cY = getHeight() / 2f;
                float x = event.getX();
                float y = event.getY();
                mMatrix.reset();
                float kx = (x - cX) / getWidth();
                float ky = (y - cY) / getHeight();
                mMatrix.setSkew(kx, ky, getWidth() / 2f, getHeight() / 2f);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mMatrix.reset();
                break;
            default:
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
