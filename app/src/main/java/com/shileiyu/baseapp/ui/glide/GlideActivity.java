package com.shileiyu.baseapp.ui.glide;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.base.BaseApp;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GlideActivity extends BaseActivity {
    private static final String URL = "http://a.hiphotos.baidu.com/image/h%3D300/sign=2fede0fa75310a55db24d8f487444387/09fa513d269759eee5b61ac2befb43166c22dfd1.jpg";

    @BindView(R.id.act_glide_img)
    ImageView mImg;
    @BindView(R.id.act_glide_img2)
    ImageView mImg2;
    @BindView(R.id.act_glide_img3)
    ImageView mImg3;
    @BindView(R.id.act_glide_img4)
    ImageView mImg4;
    @BindView(R.id.act_glide_img5)
    ImageView mImg5;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.act_glide_img
            , R.id.act_glide_img2
            , R.id.act_glide_img3
            , R.id.act_glide_download_only
            , R.id.act_glide_img4
            , R.id.act_glide_img5
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_glide_img:
                Glide.with(this).load(new GlideUrl(URL)).into(mImg);
                break;
            case R.id.act_glide_img2:
                Glide.with(this).load(new GlideUrl("http://p1.pstatp.com/large/166200019850062839d3")).into(mImg2);
                break;
            case R.id.act_glide_img3:
                Glide.with(this)
                        .load(URL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(new MySimpleTarget());
                break;
            case R.id.act_glide_download_only:
                Glide.with(this)
                        .load(URL)
                        .downloadOnly(new FileTarget());
                break;
            case R.id.act_glide_img4:
                transform();
                break;
            case R.id.act_glide_img5:
                transform2();
                break;
            default:
                break;
        }

    }

    private Transformation[] arr = new Transformation[]{
            new BlurTransformation(BaseApp.appContext, 30)
            , new ColorFilterTransformation(this, Color.BLACK)
            , new CropCircleTransformation(this)
            , new CropSquareTransformation(this)
            , new CropTransformation(this)
            , new GrayscaleTransformation(this)
            , new RoundedCornersTransformation(this, 20, 0)
    };
    private int index = 0;

    private void transform2() {
        Transformation t = arr[index % arr.length];
        index++;
        showToast(t.getClass().getName());
        Glide.with(this).load(URL).bitmapTransform(t).into(mImg5);

    }

    private void transform() {
        Glide.with(this)
                .load(URL)
                .bitmapTransform(new RoundBitmapTransformation(this, 30))
                .into(mImg4);
    }

    private final class FileTarget extends SimpleTarget<File> {

        @Override
        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
            showToast(resource.getAbsolutePath());
        }
    }

    private final class MySimpleTarget extends SimpleTarget<GlideDrawable> {

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            mImg3.setImageDrawable(resource);
        }
    }
}
