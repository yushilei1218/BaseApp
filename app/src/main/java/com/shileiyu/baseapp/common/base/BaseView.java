package com.shileiyu.baseapp.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.util.Util;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class BaseView implements IBaseView {

    private TargetWrapper mTarget;

    private OperateView mOperateImpl;

    private String mTag;

    private int mTaskId;

    public BaseView(Activity activity) {
        saveTagAndTaskId(activity);
        mTarget = new ActivityWrapper(activity);
        init();
    }

    public BaseView(Fragment fragment) {
        saveTagAndTaskId(fragment);
        mTarget = new FragmentWrapper(fragment);
        init();
    }

    private void saveTagAndTaskId(Object target) {
        String tag = target.getClass().getSimpleName();
        if (tag.length() > 23) {
            tag = tag.substring(0, 22);
        }
        mTag = tag;
        mTaskId = target.hashCode();
    }

    private void init() {
        View operateLayout = mTarget.findViewById(R.id.operate_layout);
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

    private Dialog mLoadDialog;

    @Override
    public void showDialogLoading(String msg) {
        Activity activity = getActivity();
        if (activity != null && (!activity.isFinishing())) {
            //notifyDataChanged dialog
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
        return mTag;
    }

    @Override
    public int taskId() {
        return mTaskId;
    }

    @Override
    public LifecycleProvider getLifecycle() {
        return null;
    }

}
