package com.shileiyu.baseapp.ui.lagou;

import com.shileiyu.baseapp.common.mvp.BasePresenter;

/**
 * @author shilei.yu
 * @since on 2018/4/2.
 */

public class LaGouPresenter extends BasePresenter<LaGouContract.IView> implements LaGouContract.IPresenter {
    final LaGouContract.IModel mModel;

    public LaGouPresenter(LaGouContract.IView view) {
        super(view);
        mModel = new LaGouModel();
    }
}
