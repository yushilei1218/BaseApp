package com.shileiyu.baseapp.common.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.shileiyu.baseapp.BuildConfig;
import com.shileiyu.baseapp.common.db.DbClient;
import com.squareup.leakcanary.LeakCanary;

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

        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
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
