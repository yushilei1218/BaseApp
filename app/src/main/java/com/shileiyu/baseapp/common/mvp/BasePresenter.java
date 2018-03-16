package com.shileiyu.baseapp.common.mvp;

import com.shileiyu.baseapp.common.base.IBaseView;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class BasePresenter<T extends IBaseView> implements IBasePresenter {
    protected T view;

    public BasePresenter(T view) {
        this.view = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
