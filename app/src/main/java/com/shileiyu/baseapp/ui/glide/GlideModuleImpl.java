package com.shileiyu.baseapp.ui.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.orhanobut.logger.Logger;
import com.shileiyu.baseapp.common.net.Client;

import java.io.InputStream;

/**
 * @author shilei.yu
 * @since on 2018/3/28.
 */

public class GlideModuleImpl implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        Logger.d("applyOptions");
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "testGlide", DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        Logger.d("registerComponents");
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory(Client.instance().getClient()));
    }
}
