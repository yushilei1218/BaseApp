package com.shileiyu.baseapp.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * @author shilei.yu
 * @since on 2018/3/20.
 */

public class LoadRecyclerView extends RecyclerView {

    private boolean isSlidingDown = false;

    private FooterAdapter mFooterAdapter = null;

    private OnLoadMoreListener mMoreListener;


    private boolean isLoading = false;

    public LoadRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setMoreListener(OnLoadMoreListener moreListener) {
        mMoreListener = moreListener;
    }

    private void init(Context context) {

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (isSlidingDown && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    processScrollDown();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                isSlidingDown = dy >= 0;
            }
        });
    }

    private void processScrollDown() {
        if (mFooterAdapter == null) {
            return;
        }
        if (mMoreListener == null) {
            return;
        }
        LayoutManager manager = getLayoutManager();

        int totalItemCount = manager.getItemCount();
        int lastVisibleItem = 0;
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) manager;
            lastVisibleItem = llm.findLastCompletelyVisibleItemPosition();

        } else if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) manager;
            int[] itemPositions = sglm.findLastCompletelyVisibleItemPositions(null);
            lastVisibleItem = findMax(itemPositions);
        }
        if (lastVisibleItem == totalItemCount - 1) {
            if (!isLoading) {
                isLoading = true;
                mFooterAdapter.showFooter();
                mMoreListener.onLoadMore();
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void hideFooter() {
        isLoading = false;
        if (mFooterAdapter != null) {
            mFooterAdapter.hideFooter();
        }
    }

    public void showFooter() {
        if (mFooterAdapter == null) {
            return;
        }
        if (isLoading) {
            return;
        }
        mFooterAdapter.showFooter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof FooterAdapter) {
            mFooterAdapter = (FooterAdapter) adapter;
        }
        super.setAdapter(adapter);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
