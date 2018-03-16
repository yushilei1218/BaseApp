package com.shileiyu.baseapp.common.net;

import okhttp3.OkHttpClient;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class Client {
    private static Client sClient;
    private OkHttpClient okClient;

    private Client() {
        okClient = new OkHttpClient.Builder().build();
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
