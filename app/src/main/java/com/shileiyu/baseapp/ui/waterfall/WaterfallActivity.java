package com.shileiyu.baseapp.ui.waterfall;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.enums.LoadStyle;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;
import com.shileiyu.baseapp.common.util.Util;
import com.shileiyu.baseapp.common.widget.BaseViewHolder;
import com.shileiyu.baseapp.common.widget.FooterAdapter;
import com.shileiyu.baseapp.common.widget.ItemDelegate;
import com.shileiyu.baseapp.common.widget.LoadRecyclerView;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.act_water_fall_xuan_ting_tv)
    TextView mXuanTingTv;
    @BindView(R.id.act_water_fall_coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.act_water_fall_ad_flipper)
    ViewFlipper mAdFlipper;

    private FooterAdapter mAdapter;

    private View.OnClickListener mAdOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null && v.getTag() instanceof ADBean) {
                showToast(((ADBean) v.getTag()).content);
            }
        }
    };

    private boolean mIsCanDrag = true;

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

        ViewGroup.LayoutParams params = mAppBar.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (behavior instanceof AppBarLayout.Behavior) {
                ((AppBarLayout.Behavior) behavior).setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                    @Override
                    public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                        mIsCanDrag = appBarLayout.getBottom() > mXuanTing.getHeight();
                        return mIsCanDrag;
                    }
                });
            }
        }
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    manager.invalidateSpanAssignments();
                }
            }
        });

        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.load(true, LoadStyle.LOAD_REFRESH);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return !mIsCanDrag && super.checkCanDoRefresh(frame, content, header);
            }
        });
        presenter.onStart();
    }

    @Override
    public void bind(List<WaterfallBean> root) {
        mAdapter.addRoot(root);
    }

    @Override
    public void showAd(List<TwoTuple<ADBean, ADBean>> ads) {
        mAdFlipper.removeAllViews();
        boolean empty = Util.isEmpty(ads);
        if (!empty) {
            addChild2Flipper(ads);
            mAdFlipper.startFlipping();
        }
        mAdFlipper.setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    private void addChild2Flipper(List<TwoTuple<ADBean, ADBean>> ads) {
        for (TwoTuple<ADBean, ADBean> ad : ads) {
            View child = LayoutInflater.from(this).inflate(R.layout.item_ad, mAdFlipper, false);
            TextView tv1 = child.findViewById(R.id.item_ad_tv1);
            TextView tv2 = child.findViewById(R.id.item_ad_tv2);
            showSingleAd(tv1, ad.v);
            showSingleAd(tv2, ad.t);
            mAdFlipper.addView(child);
        }
    }

    private void showSingleAd(TextView tv1, ADBean v) {
        tv1.setVisibility(v == null ? View.INVISIBLE : View.VISIBLE);
        tv1.setOnClickListener(v == null ? null : mAdOnClickListener);
        tv1.setText(v == null ? null : v.content);
        tv1.setTag(v == null ? null : v);
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


    @OnClick({R.id.act_water_fall_xuan_ting_tv, R.id.act_water_fall_xuan_ting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_water_fall_xuan_ting_tv:
                mAppBar.setExpanded(true, true);
                break;
            case R.id.act_water_fall_xuan_ting:
                break;
            default:
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
