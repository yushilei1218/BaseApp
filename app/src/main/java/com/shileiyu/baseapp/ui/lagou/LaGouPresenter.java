package com.shileiyu.baseapp.ui.lagou;

import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.common.mvp.BasePresenter;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/4/2.
 */

public class LaGouPresenter extends BasePresenter<LaGouContract.IView> implements LaGouContract.IPresenter {
    final LaGouContract.IModel mModel;

    public LaGouPresenter(LaGouContract.IView view) {
        super(view);
        mModel = new LaGouModel();
    }

    @Override
    public void onStart() {
        loadAd();
    }

    @Override
    public void loadAd() {
        mModel.loadAd(new ICallBack<List<TwoTuple<ADBean, ADBean>>>() {
            @Override
            public void call(List<TwoTuple<ADBean, ADBean>> data) {
                view.showAd(data);
            }
        });
    }
}
