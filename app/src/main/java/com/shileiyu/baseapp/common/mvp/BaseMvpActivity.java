package com.shileiyu.baseapp.common.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shileiyu.baseapp.common.base.BaseActivity;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class BaseMvpActivity<T extends IBasePresenter> extends BaseActivity {
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = getPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    protected abstract T getPresenter();
}
