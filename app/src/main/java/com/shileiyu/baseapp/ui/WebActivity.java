package com.shileiyu.baseapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.widget.RoundConWebView;

public class WebActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        WebView web = (WebView) findViewById(R.id.web);

        web.loadUrl("http://bang.tx3.163.com");

        //bugfix 7
    }
}
