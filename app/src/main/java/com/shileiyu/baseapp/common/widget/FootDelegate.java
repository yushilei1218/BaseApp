package com.shileiyu.baseapp.common.widget;

import android.view.View;
import android.widget.TextView;

import com.shileiyu.baseapp.R;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public class FootDelegate extends ItemDelegate<Foot> {
    @Override
    protected int layoutId() {
        return R.layout.item_footer;
    }

    @Override
    protected void bindView(int position, BaseViewHolder holder, Foot data) {
        View progress = holder.find(R.id.item_footer_loading);
        TextView tv = holder.find(R.id.item_footer_tv);
        FootState state = data.state;
        if (state == FootState.NO_MORE) {
            tv.setText("没有更多啦");
            tv.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        } else if (state == FootState.LOADING) {
            progress.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            tv.setVisibility(View.GONE);
        }
    }
}
