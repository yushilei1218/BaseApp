package com.shileiyu.baseapp.common.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public abstract class ItemDelegate<T> {

    protected abstract int layoutId();

    public final BaseViewHolder create(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId(), parent, false);
        return new BaseViewHolder(inflate);
    }

    protected abstract void bindView(int position, BaseViewHolder holder, T data);
}
