package com.shileiyu.baseapp.common.bean;

/**
 * 元组
 *
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class TwoTuple<T, V> {
    public final T first;
    public final V second;

    public TwoTuple(T t, V second) {
        this.first = t;
        this.second = second;
    }

    public TwoTuple(TwoTuple<T, V> tuple) {
        first = tuple.first;
        second = tuple.second;
    }
}
