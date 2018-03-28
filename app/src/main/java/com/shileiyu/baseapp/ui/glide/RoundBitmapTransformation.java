package com.shileiyu.baseapp.ui.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * @author shilei.yu
 * @since on 2018/3/28.
 */

public class RoundBitmapTransformation extends BitmapTransformation {
    private int radius;

    public RoundBitmapTransformation(Context context, int radius) {
        super(context);
        this.radius = radius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) {
            return null;
        }
        final Bitmap toReuse = pool.get(outWidth, outHeight, toTransform.getConfig() != null
                ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);

        Bitmap transformed;
        if (toReuse != null) {
            transformed = toReuse;
        } else {
            transformed = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(transformed);

        Paint paint = new Paint();

        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, toTransform.getWidth(), toTransform.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        if (toReuse != null && !pool.put(toReuse)) {
            toReuse.recycle();
        }

        return transformed;

    }

    @Override
    public String getId() {
        return "com.shileiyu.baseapp.ui.glide.RoundBitmapTransformation" + radius;
    }
}
