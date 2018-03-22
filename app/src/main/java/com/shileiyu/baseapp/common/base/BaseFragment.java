package com.shileiyu.baseapp.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.shileiyu.baseapp.common.net.pool.NetPool;
import com.shileiyu.baseapp.common.util.Util;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class BaseFragment extends RxFragment implements IRxFragmentBaseView {

    private BaseView mBaseView;

    private Unbinder mBinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinder = ButterKnife.bind(this, view);

        mBaseView = new BaseView(this);

        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    public void onDestroy() {
        if (mBinder != null) {
            mBinder.unbind();
        }
        NetPool.cancel(taskId());
        super.onDestroy();
    }

    @Override
    public void onHide() {
        mBaseView.onHide();
    }

    @Override
    public void onLoading(CharSequence msg) {
        mBaseView.onLoading(msg);
    }

    @Override
    public void showError(int imgRid, CharSequence msg, CharSequence btnText, View.OnClickListener btnListener) {
        mBaseView.showError(imgRid, msg, btnText, btnListener);
    }

    @Override
    public void showDialogLoading(String msg) {
        mBaseView.showDialogLoading(msg);
    }

    @Override
    public void hideDialogLoading() {
        mBaseView.hideDialogLoading();
    }

    @Override
    public void showToast(String msg) {
        Util.toast(msg);
    }

    @Override
    public void doLogin(int code, String msg) {
        mBaseView.doLogin(code, msg);
    }

    @Override
    public String getTAG() {
        return mBaseView.getTAG();
    }

    @Override
    public int taskId() {
        return this.hashCode();
    }
}
