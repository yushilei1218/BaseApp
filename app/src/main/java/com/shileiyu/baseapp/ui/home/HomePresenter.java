package com.shileiyu.baseapp.ui.home;

import com.shileiyu.baseapp.common.mvp.BasePresenter;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class HomePresenter extends BasePresenter<HomeConstract.IView> implements HomeConstract.IPresenter {
    HomePresenter(HomeConstract.IView view) {
        super(view);
    }
}
