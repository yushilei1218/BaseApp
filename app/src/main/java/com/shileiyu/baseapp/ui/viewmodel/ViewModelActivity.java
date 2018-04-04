package com.shileiyu.baseapp.ui.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseActivity;
import com.shileiyu.baseapp.common.bean.BeanA;
import com.shileiyu.baseapp.common.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ViewModelActivity extends BaseActivity {


    @BindView(R.id.act_view_model_tv)
    TextView mTv;
    private MyViewModel mModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_model;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.act_view_model_layout, new ViewModelFragment()).commitAllowingStateLoss();

        mModel = ViewModelProviders.of(this).get(MyViewModel.class);
        mModel.getBeanALiveData().observe(this, new Observer<BeanA>() {
            @Override
            public void onChanged(@Nullable BeanA beanA) {
                mTv.setText(Util.toJson(beanA));
            }
        });
    }

    @OnClick({R.id.act_view_model_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_view_model_get:
                mModel.loadBeanAs();
                break;
            default:
                break;
        }
    }
}
