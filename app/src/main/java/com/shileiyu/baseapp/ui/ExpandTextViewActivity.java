package com.shileiyu.baseapp.ui;


import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;
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
    @BindView(R.id.act_expand_tag)
    TextView tagTv;
    @BindView(R.id.act_expand_test)
    TextView testTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_expand_text_view;
    }

    @Override
    protected void initView() {
        actExpandTv.setListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpand(boolean expand) {
                tagTv.setVisibility(expand ? View.GONE : View.VISIBLE);
            }
        });
        String source = "<p>任职要求：</p><p>1.刷卡机倒垃圾</p><p>2.倨傲四大剌史莱克</p><p>3.8273u8923</p><p><br/></p><p>智能要求：</p><p>2.骄傲看你的</p><p>4.脑袋快</p>";

        Spanned spanned = Html.fromHtml(
                source);
        String s = spanned.toString();

        testTv.setText(s);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Spanned spanned1 = Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT);
            String text = spanned1.toString();
            testTv.append(text);
        }

    }


    @OnClick({R.id.act_expand_tv, R.id.act_expand_tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_expand_tv:
                Toast.makeText(this, "tv1" + actExpandTv.isExpand(), Toast.LENGTH_SHORT).show();
                actExpandTv.setExpand(!actExpandTv.isExpand());
                break;
            case R.id.act_expand_tv2:
                Toast.makeText(this, "tv1" + actExpandTv2.isExpand(), Toast.LENGTH_SHORT).show();
                actExpandTv2.setExpand(!actExpandTv2.isExpand());
                break;
            default:
                break;
        }
    }
}
