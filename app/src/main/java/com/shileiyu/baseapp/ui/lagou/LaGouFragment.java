package com.shileiyu.baseapp.ui.lagou;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.base.BaseFragment;
import com.shileiyu.baseapp.common.widget.FooterAdapter;
import com.shileiyu.baseapp.common.widget.LoadRecyclerView;
import com.shileiyu.baseapp.ui.lagou.bean.ResumeBean;
import com.shileiyu.baseapp.ui.lagou.delegate.ResumeBeanDelegate;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author shilei.yu
 */
public class LaGouFragment extends BaseFragment {

    @BindView(R.id.fg_la_gou_rl)
    LoadRecyclerView mRecycler;
    @BindView(R.id.fg_la_gou_ptr)
    PtrClassicFrameLayout mPtr;

    CanDragListener mListener;

    public LaGouFragment() {
        // Required empty public constructor
    }

    public void setListener(CanDragListener listener) {
        mListener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_la_gou;
    }

    @Override
    protected void initView() {
        mPtr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mListener != null) {
                    return mListener.isCanDrag() && super.checkCanDoRefresh(frame, content, header);
                }
                return super.checkCanDoRefresh(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtr.refreshComplete();
                    }
                }, 1000);
            }
        });

        FooterAdapter adapter = new FooterAdapter();

        adapter.setMatch(ResumeBean.class, new ResumeBeanDelegate());
        List<ResumeBean> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new ResumeBean("工程师++" + i));
        }
        adapter.addRoot(data);
        mRecycler.setAdapter(adapter);
    }

    public void setExpanded() {
        if (mRecycler.getChildCount() > 0) {
            mRecycler.smoothScrollToPosition(0);
        }
    }

    public interface CanDragListener {
        boolean isCanDrag();
    }
}
