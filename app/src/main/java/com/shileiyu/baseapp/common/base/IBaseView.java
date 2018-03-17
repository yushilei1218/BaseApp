package com.shileiyu.baseapp.common.base;

import android.app.Activity;
import android.view.View;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public interface IBaseView extends IOperateView {

    void showDialogLoading(String msg);

    void hideDialogLoading();

    void showToast(String msg);

    void doLogin(int code, String msg);

    <T extends View> T findView(int rid);

    Activity getActivity();

    String getTAG();

    int taskId();
}
