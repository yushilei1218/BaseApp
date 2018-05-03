package com.shileiyu.baseapp.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.shileiyu.baseapp.R;

/**
 * @author shilei.yu
 * @since on 2018/5/3.
 */
public class TabView extends View {
    Matrix mMatrix = new Matrix();
    Bitmap mBitmap;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.smiling);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = getWidth() / 2f - mBitmap.getWidth() / 2f;
        float y = getHeight() / 2f - mBitmap.getHeight() / 2f;
        canvas.translate(x, y);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
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
                mMatrix.setSkew(kx, ky, mBitmap.getWidth() / 2f, mBitmap.getHeight() / 2f);
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
