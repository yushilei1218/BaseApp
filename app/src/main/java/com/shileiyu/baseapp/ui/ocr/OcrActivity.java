package com.shileiyu.baseapp.ui.ocr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.callback.ICallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OcrActivity extends BaseActivity {


    @BindView(R.id.act_ocr_tv)
    TextView mActOcrTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ocr;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.act_ocr_btn)
    public void onViewClicked() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ocr2);
        OcrUtil.ScanEnglish(bitmap, new ICallBack<String>() {
            @Override
            public void call(final String data) {
                Logger.d("call data=" + data);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mActOcrTv.setText(data);
                    }
                });
            }
        });
    }
}
