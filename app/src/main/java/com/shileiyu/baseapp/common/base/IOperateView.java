package com.shileiyu.baseapp.common.base;

import android.view.View;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public interface IOperateView {
    void onHide();

    void onLoading(CharSequence msg);

    void showError(int imgRid, CharSequence msg, CharSequence btnText, View.OnClickListener btnListener);
}
