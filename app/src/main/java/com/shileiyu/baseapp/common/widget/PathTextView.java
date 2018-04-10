package com.shileiyu.baseapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;


/**
 * @author shilei.yu
 * @since on 2018/4/10.
 */
public class PathTextView extends AppCompatTextView {

    private TextPaint mPaint;
    private Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path();
    private Matrix mMatrix = new Matrix();

    public PathTextView(Context context) {
        super(context);
    }

    public PathTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = getPaint();
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeJoin(Paint.Join.ROUND);
        mPaint2.setColor(Color.BLUE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        String s = getText().toString();
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(s)) {
            mPath.reset();
            mMatrix.reset();
            mPaint.getTextPath(s, 0, s.length(), 100, 100, mPath);
            mMatrix.setScale(1.2f, 1.2f);
            boolean empty = mPath.isEmpty();

            Logger.d("empty=" + empty);
            if (!empty) {


                canvas.drawPath(mPath, mPaint2);
            }
        }
    }
}
