package com.shileiyu.baseapp.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.widget.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpandTextViewActivity extends BaseActivity {


    @BindView(R.id.act_expand_tv)
    ExpandableTextView actExpandTv;
    @BindView(R.id.act_expand_tv2)
    ExpandableTextView actExpandTv2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_expand_text_view;
    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.act_expand_tv, R.id.act_expand_tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_expand_tv:
                Toast.makeText(this, "tv1"+actExpandTv.isExpand(), Toast.LENGTH_SHORT).show();
                actExpandTv.setExpand(!actExpandTv.isExpand());
                break;
            case R.id.act_expand_tv2:
                Toast.makeText(this, "tv1"+actExpandTv2.isExpand(), Toast.LENGTH_SHORT).show();
                actExpandTv2.setExpand(!actExpandTv2.isExpand());
                break;
            default:
                break;
        }
    }
}
