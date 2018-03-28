package com.shileiyu.baseapp.ui.glide;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * @author shilei.yu
 * @since on 2018/3/28.
 */

public class OkHttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private OkHttpClient mClient;

    public OkHttpGlideUrlLoader(OkHttpClient client) {
        mClient = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new OkHttpFetcher(mClient, model);
    }

    public static final class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
        private OkHttpClient client;

        public Factory(OkHttpClient client) {
            this.client = client;
        }

        public Factory() {
            client = new OkHttpClient();
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OkHttpGlideUrlLoader(client);
        }

        @Override
        public void teardown() {

        }
    }
}
