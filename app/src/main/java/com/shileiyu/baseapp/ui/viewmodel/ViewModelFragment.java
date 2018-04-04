package com.shileiyu.baseapp.ui.viewmodel;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseFragment;
import com.shileiyu.baseapp.common.bean.BeanA;
import com.shileiyu.baseapp.common.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author shilei.yu
 */
public class ViewModelFragment extends BaseFragment {


    @BindView(R.id.fg_view_model_tv)
    TextView mFgTv;

    private MyViewModel mModel;

    public ViewModelFragment() {
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_model;
    }

    @Override
    protected void initView() {
        mModel = ViewModelProviders.of(getActivity()).get(MyViewModel.class);
        mModel.getBeanALiveData().observe(this, new Observer<BeanA>() {
            @Override
            public void onChanged(@Nullable BeanA beanA) {
                mFgTv.setText(Util.toJson(beanA));
            }
        });
    }

    @OnClick(R.id.fg_view_model_get)
    public void onViewClicked() {
        mModel.loadBeanAs();
    }
}
