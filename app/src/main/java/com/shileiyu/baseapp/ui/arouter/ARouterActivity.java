package com.shileiyu.baseapp.ui.arouter;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;


public class ARouterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        if (data != null) {
            Log.d("ARouterActivity", data.toString());
            ARouter.getInstance().build(data).navigation();
        }
        finish();
    }
}
