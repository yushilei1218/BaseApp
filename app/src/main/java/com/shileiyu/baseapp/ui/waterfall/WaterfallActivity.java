package com.shileiyu.baseapp.ui.waterfall;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.ThreeTuple;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.enums.LoadState;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;
import com.shileiyu.baseapp.common.widget.BaseViewHolder;
import com.shileiyu.baseapp.common.widget.FooterAdapter;
import com.shileiyu.baseapp.common.widget.ItemDelegate;
import com.shileiyu.baseapp.common.widget.LoadRecyclerView;
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
    LoadRecyclerView mRecycler;
    @BindView(R.id.act_water_fall_swipe)
    SwipeRefreshLayout mSwipeLayout;
    private FooterAdapter mAdapter;

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
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new FooterAdapter();

        mAdapter.setMatch(WaterfallBean.class, new WaterDelegate());

        mRecycler.setAdapter(mAdapter);

        mRecycler.setMoreListener(new LoadRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.load(false, LoadState.LOAD_MORE);
            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.load(true, LoadState.LOAD_REFRESH);
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
            case LOAD_INNER:
                if (isShow) {
                    onLoading("加载中...");
                } else {
                    onHide();
                }
                break;
            case LOAD_DIALOG:
                if (isShow) {
                    showDialogLoading("加载中");
                } else {
                    hideDialogLoading();
                }
                break;
            case LOAD_REFRESH:
                if (!isShow) {
                    mSwipeLayout.setRefreshing(false);
                }
                break;
            case LOAD_MORE:
                if (!isShow) {
                    mRecycler.hideFooter();
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

    private final class WaterDelegate extends ItemDelegate<WaterfallBean> {

        @Override
        protected int layoutId() {
            return R.layout.item_water_fall;
        }

        @Override
        protected void bindView(int position, BaseViewHolder holder, WaterfallBean data) {
            ImageView img = holder.find(R.id.item_water_img);
            img.setImageResource(data.rid);
        }
    }
}
