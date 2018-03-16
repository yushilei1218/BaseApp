package com.shileiyu.baseapp.common.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shileiyu.baseapp.R;


/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

class OperateView implements IOperateView {

    private View mRoot = null;
    private boolean hasOperateView = false;

    private View mErrorLayout;
    private TextView mErrorBtn;
    private TextView mErrorTv;
    private ImageView mErrorImg;

    private View mLoadingLayout;
    private TextView mLoadTv;

    OperateView(View root) {
        if (root != null) {
            hasOperateView = true;
            this.mRoot = root;
            mLoadingLayout = mRoot.findViewById(R.id.operate_loading_layout);
            mLoadTv = mRoot.findViewById(R.id.operate_loading_tv);
            mErrorLayout = mRoot.findViewById(R.id.operate_error_layout);
            mErrorBtn = mRoot.findViewById(R.id.operate_error_btn);
            mErrorImg = mRoot.findViewById(R.id.operate_error_img);
            mErrorTv = mRoot.findViewById(R.id.operate_error_tv);

        }
    }

    @Override
    public void onHide() {
        if (hasOperateView) {
            mRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoading(CharSequence msg) {
        if (hasOperateView) {
            mRoot.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.GONE);
            mLoadingLayout.setVisibility(View.VISIBLE);
            mLoadTv.setText(msg);
        }
    }

    @Override
    public void showError(int imgRid, CharSequence msg, CharSequence btnText, View.OnClickListener btnListener) {
        if (hasOperateView) {
            mRoot.setVisibility(View.VISIBLE);
            mErrorLayout.setVisibility(View.VISIBLE);
            mLoadingLayout.setVisibility(View.GONE);
            if (imgRid != -1) {
                mErrorImg.setImageResource(imgRid);
            }
            mErrorTv.setText(msg);
            mErrorBtn.setText(btnText);
            mErrorBtn.setOnClickListener(btnListener);
        }
    }
}
