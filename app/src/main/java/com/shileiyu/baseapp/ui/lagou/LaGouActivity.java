package com.shileiyu.baseapp.ui.lagou;


import android.animation.ArgbEvaluator;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;
import com.shileiyu.baseapp.common.util.Util;
import com.shileiyu.baseapp.ui.lagou.delegate.PtrUIAdapter;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author shilei.yu
 * @date 2018/04/02
 */
public class LaGouActivity extends BaseMvpActivity<LaGouContract.IPresenter> implements LaGouContract.IView {

    @BindView(R.id.act_la_gou_ad_flipper)
    ViewFlipper mADFlipper;
    @BindView(R.id.act_la_gou_app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.act_la_gou_content_layout)
    FrameLayout mContentLayout;
    @BindView(R.id.act_la_gou_coordinator)
    CoordinatorLayout mOutCoordinator;
    @BindView(R.id.act_la_gou_out_ptr)
    PtrClassicFrameLayout mOutPtr;
    @BindView(R.id.act_la_gou_out_search_bar)
    LinearLayout mSearchBar;
    @BindView(R.id.act_la_gou_content_title)
    TextView mContentTitle;
    @BindView(R.id.act_la_gou_fixed_header)
    View mFixedHeader;

    private boolean isFullOpen = true;
    private boolean isFullClose = false;

    private LaGouFragment mFragment;

    private ArgbEvaluator mSearchBarEvaluator = new ArgbEvaluator();

    private View.OnClickListener mAdClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag != null && tag instanceof ADBean) {
                showToast(tag.toString());
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lagou;
    }

    @Override
    protected void initView() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                isFullOpen = verticalOffset == 0;
                int range = appBarLayout.getTotalScrollRange();
                isFullClose = Math.abs(verticalOffset) == range;
                if (isFullClose) {
                    mContentTitle.setText("返回");
                } else {
                    mContentTitle.setText("职位列表");
                }
                float alpha = (float) Math.abs(verticalOffset) / range;

                mSearchBar.setBackgroundColor((Integer) mSearchBarEvaluator.evaluate(alpha, 0x00ffffff, 0xffffffff));
            }
        });
        mOutPtr.addPtrUIHandler(new PtrUIAdapter() {
            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                mSearchBar.setVisibility(View.GONE);
            }

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mSearchBar.setVisibility(View.VISIBLE);
            }
        });
        mOutPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return isFullOpen && super.checkCanDoRefresh(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mOutPtr.refreshComplete();
                    }
                }, 1000);
            }
        });
        mFragment = new LaGouFragment();
        mFragment.setListener(new LaGouFragment.CanDragListener() {
            @Override
            public boolean isCanDrag() {
                return isFullClose;
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.act_la_gou_content_layout, mFragment).commitAllowingStateLoss();

        presenter.onStart();
    }

    @Override
    public void showAd(List<TwoTuple<ADBean, ADBean>> data) {
        mADFlipper.removeAllViews();
        if (!Util.isEmpty(data)) {
            for (TwoTuple<ADBean, ADBean> tuple : data) {
                View child = LayoutInflater.from(this).inflate(R.layout.item_ad_lagou, mADFlipper, false);
                View ad1Layout = child.findViewById(R.id.item_ad1_layout);
                TextView ad1TagTv = child.findViewById(R.id.item_ad1_tag_tv);
                TextView ad1TitleTv = child.findViewById(R.id.item_ad1_title_tv);
                View ad2Layout = child.findViewById(R.id.item_ad2_layout);
                TextView ad2TagTv = child.findViewById(R.id.item_ad2_tag_tv);
                TextView ad2TitleTv = child.findViewById(R.id.item_ad2_title_tv);
                showSingleAd(tuple.first, ad1Layout, ad1TagTv, ad1TitleTv);
                showSingleAd(tuple.second, ad2Layout, ad2TagTv, ad2TitleTv);
                mADFlipper.addView(child);
            }
            mADFlipper.startFlipping();
        }
    }

    private void showSingleAd(ADBean v, View ad1Layout, TextView ad1TagTv, TextView ad1TitleTv) {
        boolean valid = v != null;
        ad1Layout.setVisibility(valid ? View.VISIBLE : View.INVISIBLE);
        ad1TagTv.setText(valid ? v.tag : null);
        ad1TitleTv.setText(valid ? v.content : null);
        ad1Layout.setTag(v);
        ad1Layout.setOnClickListener(valid ? mAdClickListener : null);
    }

    @OnClick({
            R.id.act_la_gou_fixed_header
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_la_gou_fixed_header:
                mAppBar.setExpanded(true, true);
                mFragment.setExpanded();
                break;
            default:
                break;
        }
    }

    @Override
    protected LaGouContract.IPresenter getPresenter() {
        return new LaGouPresenter(this);
    }

}
