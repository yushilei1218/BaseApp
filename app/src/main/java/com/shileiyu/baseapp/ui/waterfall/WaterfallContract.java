package com.shileiyu.baseapp.ui.waterfall;

import com.shileiyu.baseapp.common.base.IBaseView;
import com.shileiyu.baseapp.common.base.IRxActivityBaseView;
import com.shileiyu.baseapp.common.bean.ThreeTuple;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.enums.LoadStyle;
import com.shileiyu.baseapp.common.mvp.IBasePresenter;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public interface WaterfallContract {
    interface IPresenter extends IBasePresenter {
        void load(boolean isRefresh, LoadStyle state);

        void loadAd();
    }

    interface IView extends IRxActivityBaseView {
        void bind(List<WaterfallBean> root);

        void showAd(List<TwoTuple<ADBean, ADBean>> ads);

        void changeLoadState(LoadStyle state, boolean isShow);

        void notifyDataChanged(TwoTuple<LoadStyle, DataState> tuple);
    }

    interface IModel {
        List<WaterfallBean> data = new ArrayList<>();

        void load(boolean isRefresh, ICallBack<TwoTuple<List<WaterfallBean>, DataState>> callback);

        void loadAd(ICallBack<List<TwoTuple<ADBean, ADBean>>> callBack);
    }
}
