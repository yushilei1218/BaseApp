package com.shileiyu.baseapp.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.util.Util;

import java.net.URL;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class BaseView implements IBaseView {

    private SparseArray<View> mViews = new SparseArray<>();

    private TargetWrapper mTarget;

    private OperateView mOperateImpl;

    public BaseView(Activity activity) {
        mTarget = new ActivityWrapper(activity);
        init();
    }

    public BaseView(Fragment fragment) {
        mTarget = new FragmentWrapper(fragment);
        init();
    }

    private void init() {
        View operateLayout = findView(R.id.operate_layout);
        mOperateImpl = new OperateView(operateLayout);
    }

    @Override
    public void onHide() {
        mOperateImpl.onHide();
    }

    @Override
    public void onLoading(CharSequence msg) {
        mOperateImpl.onLoading(msg);
    }

    @Override
    public void showError(int imgRid, CharSequence msg, CharSequence btnText, View.OnClickListener btnListener) {
        mOperateImpl.showError(imgRid, msg, btnText, btnListener);
    }

    @Override
    public void showToast(String msg) {
        Util.toast(msg);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T findView(int rid) {
        View view = mViews.get(rid);
        if (view == null) {
            view = mTarget.findViewById(rid);
            if (view != null) {
                mViews.append(rid, view);
            }
        }
        return (T) view;
    }

    private Dialog mLoadDialog;

    @Override
    public void showDialogLoading(String msg) {
        Activity activity = getActivity();
        if (activity != null && (!activity.isFinishing())) {
            //show dialog
            if (mLoadDialog == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                View v = inflater.inflate(R.layout.request_loading, null);
                mLoadDialog = new Dialog(activity, R.style.request_loading);

                mLoadDialog.setCancelable(true);
                mLoadDialog.setCanceledOnTouchOutside(false);
                mLoadDialog.setContentView(v);
            }
            TextView loadTV = mLoadDialog.findViewById(R.id.request_loading_tv);
            loadTV.setText(msg);
            if (!mLoadDialog.isShowing()) {
                mLoadDialog.show();
            }
        }
    }

    @Override
    public void hideDialogLoading() {
        if (mLoadDialog != null) {
            mLoadDialog.dismiss();
        }
    }

    @Override
    public void doLogin(int code, String msg) {
        Util.toast("re login");
    }

    @Override
    public Activity getActivity() {
        return mTarget.getActivity();
    }

    interface TargetWrapper {
        View findViewById(int rid);

        Activity getActivity();
    }

    private class FragmentWrapper implements TargetWrapper {
        private final Fragment mFragment;

        public FragmentWrapper(Fragment fragment) {
            mFragment = fragment;
        }

        @Override
        public View findViewById(int rid) {
            View parent = mFragment.getView();
            if (parent != null) {
                return parent.findViewById(rid);
            }
            return null;
        }

        @Override
        public Activity getActivity() {
            return null;
        }
    }

    private class ActivityWrapper implements TargetWrapper {
        private final Activity target;

        public ActivityWrapper(Activity target) {
            this.target = target;
        }

        @Override
        public View findViewById(int rid) {
            return target.findViewById(rid);
        }

        @Override
        public Activity getActivity() {
            return target;
        }
    }

    @Override
    public String getTAG() {
        return null;
    }

    @Override
    public int taskId() {
        return 0;
    }
}
