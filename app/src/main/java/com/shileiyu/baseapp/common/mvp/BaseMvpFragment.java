package com.shileiyu.baseapp.common.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shileiyu.baseapp.common.base.BaseFragment;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class BaseMvpFragment<T extends IBasePresenter> extends BaseFragment {
    protected T presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = getPresenter();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    protected abstract T getPresenter();
}
