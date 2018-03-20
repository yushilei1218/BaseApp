package com.shileiyu.baseapp.common.widget;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public <T> T find(int rid) {
        View view = mViews.get(rid);
        if (view==null){
            view=itemView.findViewById(rid);
            if (view!=null) {
                mViews.put(rid, view);
            }
        }
        return (T) view;
    }
}
