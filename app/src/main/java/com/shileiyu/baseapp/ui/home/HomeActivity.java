package com.shileiyu.baseapp.ui.home;


import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;

/**
 * @author shilei.yu
 */
public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeConstract.IView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }
}
