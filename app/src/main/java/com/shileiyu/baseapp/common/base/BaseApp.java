package com.shileiyu.baseapp.common.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.shileiyu.baseapp.BuildConfig;
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

        logInit();

        DbClient.instance();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });
    }

    private void logInit() {
        FormatStrategy strategy = PrettyFormatStrategy
                .newBuilder()
                .showThreadInfo(true)
                .tag("MyLogger")
                .methodCount(0)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
