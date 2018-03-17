package com.shileiyu.baseapp.common.db;

import android.os.Handler;
import android.os.Looper;

import com.shileiyu.baseapp.common.bean.DaoSession;

/**
 * @author shilei.yu
 * @since on 2018/3/17.
 */

public abstract class ResultTask<T> extends Run {
    protected DaoSession dao = DbClient.instance().getDaoSession();

    private final static Handler HANDLER = new Handler(Looper.getMainLooper());

    protected abstract void onResult(T data);

    protected abstract T call();

    @Override
    public void run() {
        super.run();
        final T call = call();
        if (call != null) {
            HANDLER.post(new Runnable() {
                T data = call;

                @Override
                public void run() {
                    onResult(data);
                }
            });
        }
    }
}
