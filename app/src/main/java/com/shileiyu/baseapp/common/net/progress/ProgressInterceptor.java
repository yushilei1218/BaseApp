package com.shileiyu.baseapp.common.net.progress;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author shilei.yu
 * @since on 2018/3/28.
 */

public class ProgressInterceptor implements Interceptor {

    static final HashMap<String, ProgressListener> MAP = new HashMap<>();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        return response.newBuilder().body(new ProgressResponseBody(body, request.url().toString())).build();
    }

    public static void addProgressListener(String url, ProgressListener listener) {
        MAP.put(url, listener);

    }

    public static void addProgressListener(String url) {
        MAP.remove(url);
    }
}
