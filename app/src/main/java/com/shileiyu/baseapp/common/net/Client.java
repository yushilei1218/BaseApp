package com.shileiyu.baseapp.common.net;

import com.shileiyu.baseapp.common.net.progress.ProgressInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class Client {
    private static Client sClient;
    private OkHttpClient okClient;

    private Client() {
        okClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(new ProgressInterceptor())
                .build();
    }

    public static synchronized Client instance() {
        if (sClient == null) {
            sClient = new Client();
        }
        return sClient;
    }

    public OkHttpClient getClient() {
        return okClient;
    }
}
