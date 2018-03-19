package com.shileiyu.baseapp.common.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.shileiyu.baseapp.common.db.DbClient;

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

        DbClient.instance();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
               e.printStackTrace();
            }
        });
    }
}
