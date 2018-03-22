package com.shileiyu.baseapp.common.base;

import android.app.Activity;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public interface IBaseView<E> extends IOperateView {

    void showDialogLoading(String msg);

    void hideDialogLoading();

    void showToast(String msg);

    void doLogin(int code, String msg);

    Activity getActivity();

    String getTAG();

    int taskId();

    LifecycleProvider<E> getLifecycle();
}
