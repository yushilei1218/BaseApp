package com.shileiyu.baseapp.ui.waterfall;

import com.shileiyu.baseapp.common.base.IBaseView;
import com.shileiyu.baseapp.common.bean.ThreeTuple;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.enums.LoadState;
import com.shileiyu.baseapp.common.mvp.IBasePresenter;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public interface WaterfallContract {
    interface IPresenter extends IBasePresenter {
        void load(boolean isRefresh, LoadState state);
    }

    interface IView extends IBaseView {
        void bind(List<WaterfallBean> root);

        void changeLoadState(LoadState state,boolean isShow);

        void show(ThreeTuple<LoadState, List<WaterfallBean>, DataState> tuple);
    }

    interface IModel {
        static List<WaterfallBean> data = new ArrayList<>();

        void load(boolean isRefresh, ICallBack<TwoTuple<List<WaterfallBean>, DataState>> callback);
    }
}
