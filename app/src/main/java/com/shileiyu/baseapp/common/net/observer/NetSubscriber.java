package com.shileiyu.baseapp.common.net.observer;

import com.shileiyu.baseapp.common.net.cancel.SubCancelable;
import com.shileiyu.baseapp.common.net.pool.NetPool;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class NetSubscriber<T> implements Subscriber<T> {

    private SubCancelable mCancelable;
    private int taskId;

    public NetSubscriber(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void onSubscribe(Subscription s) {
        mCancelable = new SubCancelable(s);
        NetPool.add(taskId, mCancelable);
    }

    @Override
    public void onError(Throwable t) {
        NetPool.remove(mCancelable);
        onFailed(t);
    }

    public abstract void onFailed(Throwable t);

    @Override
    public void onComplete() {
        NetPool.remove(mCancelable);
    }
}
