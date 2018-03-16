package com.shileiyu.baseapp.common.net.observer;

import com.shileiyu.baseapp.common.net.cancel.DisCancelable;
import com.shileiyu.baseapp.common.net.pool.NetPool;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class NetObserver<T> implements Observer<T> {

    private final int taskId;

    public NetObserver(int taskId) {
        this.taskId = taskId;
    }

    private DisCancelable dis;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        dis = new DisCancelable(d);
        NetPool.add(taskId, dis);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        NetPool.remove(dis);
        onFailed(e);
    }

    public abstract void onFailed(Throwable e);

    @Override
    public void onComplete() {
        NetPool.remove(dis);
    }
}
