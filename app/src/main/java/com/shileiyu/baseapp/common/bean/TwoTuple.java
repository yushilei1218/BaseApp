package com.shileiyu.baseapp.common.bean;

/**
 * 元组
 *
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class TwoTuple<T, V> {
    public final T t;
    public final V v;

    public TwoTuple(T t, V v) {
        this.t = t;
        this.v = v;
    }

    public TwoTuple(TwoTuple<T, V> tuple) {
        t = tuple.t;
        v = tuple.v;
    }
}
