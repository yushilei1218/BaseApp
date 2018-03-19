package com.shileiyu.baseapp.ui.waterfall;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.ThreeTuple;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.enums.LoadState;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;
import com.shileiyu.baseapp.ui.waterfall.adapter.WaterfallAdapter;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;

import java.util.List;

import butterknife.BindView;

/**
 * 瀑布流
 *
 * @author shilei.yu
 */
public class WaterfallActivity extends BaseMvpActivity<WaterfallContract.IPresenter> implements WaterfallContract.IView {

    @BindView(R.id.act_water_fall_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.act_water_fall_swipe)
    SwipeRefreshLayout mSwipeLayout;
    private WaterfallAdapter mAdapter;

    @Override
    protected WaterfallContract.IPresenter getPresenter() {
        return new WaterfallPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_waterfall;
    }

    @Override
    protected void initView() {
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new WaterfallAdapter();
        mRecycler.setAdapter(mAdapter);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load(true, LoadState.LOAD_WIDGET);
            }
        });
        presenter.onStart();
    }

    @Override
    public void bind(List<WaterfallBean> root) {
        mAdapter.addRoot(root);
    }

    @Override
    public void changeLoadState(LoadState state, boolean isShow) {
        switch (state) {
            case LOADING:
                if (isShow) {
                    onLoading("加载中...");
                } else {
                    onHide();
                }
                break;
            case DIALOG_LOADING:
                if (isShow) {
                    showDialogLoading("加载中");
                } else {
                    hideDialogLoading();
                }
                break;
            case LOAD_WIDGET:
                if (!isShow) {
                    mSwipeLayout.setRefreshing(false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void show(ThreeTuple<LoadState, List<WaterfallBean>, DataState> tuple) {
        List<WaterfallBean> t = tuple.t;
        LoadState k = tuple.k;
        DataState v = tuple.v;
        mAdapter.notifyDataSetChanged();
        if (v == DataState.HAS_MORE) {
            showToast("有更多");
        } else {
            showToast("无更多");
        }
    }
}
