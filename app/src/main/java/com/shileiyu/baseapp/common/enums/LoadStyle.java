package com.shileiyu.baseapp.common.enums;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public enum LoadStyle {
    /**
     * 刷新控件触发的loading
     */
    LOAD_REFRESH,
    /**
     * 对话框loading
     */
    LOAD_DIALOG,
    /**
     * 内部触发loading 一般为{@link com.shileiyu.baseapp.common.base.IBaseView#onLoading(CharSequence)}
     */
    LOAD_INNER,
    /**
     * 上拉加载更多
     */
    LOAD_MORE
}
