package com.shileiyu.baseapp.common.base;

import android.app.Application;
import android.content.Context;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class BaseApp extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
