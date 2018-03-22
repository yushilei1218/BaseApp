package com.shileiyu.baseapp.common.net.observer;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.shileiyu.baseapp.common.net.cancel.SubCancelable;
import com.shileiyu.baseapp.common.net.pool.NetPool;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class NetSubscriber<T> implements Subscriber<T> {

    private SubCancelable mCancelable;
    private int taskId = -1;

    public NetSubscriber(int taskId) {
        this.taskId = taskId;
    }

    public NetSubscriber() {
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
        Logger.e("onSubscribe");
        if (taskId != -1) {
            mCancelable = new SubCancelable(s);
            NetPool.add(taskId, mCancelable);
        }
    }

    @Override
    public void onError(Throwable t) {
        Logger.e("onError");
        if (taskId != -1) {
            NetPool.remove(mCancelable);
        }
        onFailed(t);
    }

    public abstract void onFailed(Throwable t);

    @Override
    public void onComplete() {
        Logger.e("onComplete");
        if (taskId != -1) {
            NetPool.remove(mCancelable);
        }
    }
}
