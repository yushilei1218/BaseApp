package com.shileiyu.baseapp.ui;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class WebListActivity extends BaseActivity {

    private Handler mHandler;
    private Adapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_list;
    }

    int index = 0;

    @Override
    protected void initView() {
        ListView lv = findViewById(R.id.web_list);
        mAdapter = new Adapter();
        lv.setAdapter(mAdapter);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                add();
                if (index > 20) {
                    return;
                }
                mHandler.postDelayed(this, 500);

            }
        }, 500);
    }

    @SuppressLint("JavascriptInterface")
    private void add() {
        final WebView webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void show() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.data.add(webView);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }, "handler");
        webView.loadData("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                        "    <title>Document</title>\n" +
                        "    <style>\n" +
                        "        div {\n" +
                        "            width: 400px;\n" +
                        "            height: 300px;\n" +
                        "            background-color: aqua;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "    <script>\n" +
                        "        function test1() {\n" +
                        "            window.handler.click();\n" +
                        "        }\n" +
                        "        window.onload = function () {\n" +
                        "            window.handler.show();\n" +
                        "        }\n" +
                        "\n" +
                        "    </script>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
                        "    <div>\n" +
                        "        <strong id=\"iv\" onclick=\"test1()\">这是一个BTN</strong>\n" +
                        "    </div>\n" +
                        "    <p>这是一个行/r/n这是另一行 </p>\n" +
                        "</body>\n" +
                        "\n" +
                        "</html>"
                , "text/html", "UTF-8");
    }

    private final class Adapter extends BaseAdapter {

        List<WebView> data = new ArrayList<>();

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WebView webView = data.get(position);
            ViewGroup.LayoutParams LP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    200);
            webView.setLayoutParams(LP);
            return webView;
        }
    }
}
