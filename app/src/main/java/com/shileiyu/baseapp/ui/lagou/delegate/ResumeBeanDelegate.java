package com.shileiyu.baseapp.ui.lagou.delegate;

import android.view.View;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.widget.BaseViewHolder;
import com.shileiyu.baseapp.common.widget.ItemDelegate;
import com.shileiyu.baseapp.ui.lagou.bean.ResumeBean;

/**
 * @author shilei.yu
 * @since on 2018/4/2.
 */

public class ResumeBeanDelegate extends ItemDelegate<ResumeBean> {
    @Override
    protected int layoutId() {
        return R.layout.item_resume;
    }

    @Override
    protected void bindView(int position, BaseViewHolder holder, ResumeBean data) {
        TextView view = holder.find(R.id.item_rzm_name);
        view.setText(data.name);
    }
}
