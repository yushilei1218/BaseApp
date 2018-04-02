package com.shileiyu.baseapp.ui.lagou;

import com.shileiyu.baseapp.common.base.IBaseView;
import com.shileiyu.baseapp.common.base.IRxActivityBaseView;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.common.mvp.IBasePresenter;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/4/2.
 */

public interface LaGouContract {
    interface IPresenter extends IBasePresenter {
        void loadAd();
    }

    interface IView extends IRxActivityBaseView {
        void showAd(List<TwoTuple<ADBean, ADBean>> data);
    }

    interface IModel {
        void loadAd(ICallBack<List<TwoTuple<ADBean, ADBean>>> callBack);
    }
}
