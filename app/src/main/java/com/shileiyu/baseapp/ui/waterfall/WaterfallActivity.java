package com.shileiyu.baseapp.ui.waterfall;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.enums.LoadStyle;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;
import com.shileiyu.baseapp.common.widget.BaseViewHolder;
import com.shileiyu.baseapp.common.widget.FooterAdapter;
import com.shileiyu.baseapp.common.widget.ItemDelegate;
import com.shileiyu.baseapp.common.widget.LoadRecyclerView;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 瀑布流
 *
 * @author shilei.yu
 */
public class WaterfallActivity extends BaseMvpActivity<WaterfallContract.IPresenter> implements WaterfallContract.IView {

    @BindView(R.id.act_water_fall_recycler)
    LoadRecyclerView mRecycler;
    @BindView(R.id.act_water_fall_app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.act_water_fall_ptr)
    PtrClassicFrameLayout mPtr;
    @BindView(R.id.act_water_fall_xuan_ting)
    LinearLayout mXuanTing;
    @BindView(R.id.act_water_fall_coordinator)
    CoordinatorLayout mCoordinator;

    private FooterAdapter mAdapter;

    @Override
    protected WaterfallContract.IPresenter getPresenter() {
        return new WaterfallPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_waterfall;
    }

    private boolean isCanPtr = false;

    @Override
    protected void initView() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                isCanPtr = appBarLayout.getTop() - mXuanTing.getHeight() <= -verticalOffset;
                Logger.e("verticalOffset=" + verticalOffset + " appBarLayout height=" + appBarLayout.getHeight());
            }
        });
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mRecycler.setLayoutManager(manager);
        mAdapter = new FooterAdapter();

        mAdapter.setMatch(WaterfallBean.class, new WaterDelegate());

        mRecycler.setAdapter(mAdapter);

        mRecycler.setMoreListener(new LoadRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadingMore() {
                presenter.load(false, LoadStyle.LOAD_MORE);
            }
        });

        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.load(true, LoadStyle.LOAD_REFRESH);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return isCanPtr && super.checkCanDoRefresh(frame, content, header);
            }
        });
        presenter.onStart();
    }

    @Override
    public void bind(List<WaterfallBean> root) {
        mAdapter.addRoot(root);
    }

    @Override
    public void changeLoadState(LoadStyle state, boolean isShow) {
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
                    mPtr.refreshComplete();
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
    public void notifyDataChanged(TwoTuple<LoadStyle, DataState> tuple) {
        mAdapter.notifyDataSetChanged();

        switch (tuple.v) {
            case EMPTY:
                showError(-1, "首页无数据", "重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.load(true, LoadStyle.LOAD_INNER);
                    }
                });
                break;
            case NO_MORE:
                mRecycler.noMore();
                break;
            default:
                mRecycler.hideFooter();
                break;
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
            Glide.with(WaterfallActivity.this)
                    .load(data.rid)
                    .placeholder(R.mipmap.place_holder)
                    .into(img);
        }
    }
}
