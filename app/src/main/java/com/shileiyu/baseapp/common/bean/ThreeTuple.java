package com.shileiyu.baseapp.common.bean;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class ThreeTuple<K, T, V> extends TwoTuple<T, V> {
    public final K k;

    public ThreeTuple(T t, V v, K k) {
        super(t, v);
        this.k = k;
    }

    public ThreeTuple(TwoTuple<T, V> tuple, K k) {
        super(tuple);
        this.k = k;
    }
}
