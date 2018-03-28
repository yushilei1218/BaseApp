package com.shileiyu.baseapp.ui.glide;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author shilei.yu
 * @since on 2018/3/28.
 */

public class OkHttpFetcher implements DataFetcher<InputStream> {

    private OkHttpClient client;
    private GlideUrl url;
    private InputStream stream;
    private boolean isCanceled;
    private ResponseBody mBody;

    public OkHttpFetcher(OkHttpClient client, GlideUrl url) {
        this.client = client;
        this.url = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request.Builder builder = new Request.Builder();
        for (String key : url.getHeaders().keySet()) {
            builder.addHeader(key, url.getHeaders().get(key));
        }
        Request request = builder.url(url.toURL()).build();
        if (isCanceled) {
            return null;
        }

        Response response = client.newCall(request).execute();
        mBody = response.body();
        if (!response.isSuccessful() || mBody == null) {
            throw new IOException("Request failed with code: " + response.code());
        } else {
            stream = ContentLengthInputStream.obtain(mBody.byteStream(), mBody.contentLength());
        }
        return stream;
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mBody != null) {
            mBody.close();
        }
    }

    @Override
    public String getId() {
        return url.getCacheKey();
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }
}
