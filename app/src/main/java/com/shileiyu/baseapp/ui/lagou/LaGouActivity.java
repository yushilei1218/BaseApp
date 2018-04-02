package com.shileiyu.baseapp.ui.lagou;


import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;

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
    @BindView(R.id.act_la_gou_fixed_header)
    View mFixedHeader;

    private boolean isFullOpen = true;
    private boolean isFullClose = false;
    private LaGouFragment mFragment;

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
                isFullClose = Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange();
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
