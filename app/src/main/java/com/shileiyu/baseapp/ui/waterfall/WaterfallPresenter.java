package com.shileiyu.baseapp.ui.waterfall;

import com.shileiyu.baseapp.common.bean.ThreeTuple;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.enums.LoadState;
import com.shileiyu.baseapp.common.mvp.BasePresenter;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;

import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class WaterfallPresenter extends BasePresenter<WaterfallContract.IView> implements WaterfallContract.IPresenter {
    private WaterfallContract.IModel mModel;

    public WaterfallPresenter(WaterfallContract.IView view) {
        super(view);
        mModel = new WaterfallModel();
    }

    @Override
    public void onStart() {
        view.bind(WaterfallContract.IModel.data);
        load(true, LoadState.LOAD_INNER);
    }

    @Override
    public void load(boolean isRefresh, final LoadState state) {
        view.changeLoadState(state, true);

        mModel.load(isRefresh, new ICallBack<TwoTuple<List<WaterfallBean>, DataState>>() {
            @Override
            public void call(TwoTuple<List<WaterfallBean>, DataState> data) {

                view.show(new ThreeTuple<>(data, state));
                view.changeLoadState(state, false);
            }
        });
    }
}
