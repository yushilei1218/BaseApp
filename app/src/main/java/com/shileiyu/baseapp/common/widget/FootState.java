package com.shileiyu.baseapp.common.widget;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public enum FootState {
    /**
     * 没有更多了
     */
    NO_MORE,
    /**
     * 加载中
     */
    LOADING,
    /**
     * 隐藏
     */
    HIDE;

    public boolean isCanLoadMore() {
        return this == HIDE;
    }
}
