package com.shileiyu.baseapp.common.mvp;

import com.shileiyu.baseapp.common.base.IBaseView;
import com.shileiyu.baseapp.common.net.pool.NetPool;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class BasePresenter<T extends IBaseView> implements IBasePresenter {
    protected T view;
    protected int taskId;

    public BasePresenter(T view) {
        this.view = view;
        taskId = view.hashCode();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        NetPool.cancel(taskId);
    }
}
