package com.shileiyu.baseapp.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.shileiyu.baseapp.common.net.pool.NetPool;
import com.shileiyu.baseapp.common.util.ActivityTask;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements IRxActivityBaseView {

    private BaseView mBaseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidrConfig.Builder builder = new SlidrConfig.Builder();
        builder.edge(true).position(SlidrPosition.LEFT);
        Slidr.attach(this, builder.build());

        setContentView(getLayoutId());

        ActivityTask.addActivity(this);

        mBaseView = new BaseView(this);

        ButterKnife.bind(this);

        initView();

        List<BaseActivity> list = ActivityTask.getList();
        for (BaseActivity a : list) {
            Log.d(getTAG(), a.getTAG() + " window Type =" + a.getWindow().getAttributes().type);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        NetPool.cancel(taskId());
        ActivityTask.removeActivity(this);
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
    public void showToast(String msg) {
        mBaseView.showToast(msg);
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
    public Activity getActivity() {
        return this;
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

    @Override
    public LifecycleProvider<ActivityEvent> getRxLifecycle() {
        return this;
    }
}
