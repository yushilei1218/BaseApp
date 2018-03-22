package com.shileiyu.baseapp.ui.home;

import com.shileiyu.baseapp.common.base.IBaseView;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public interface HomeConstract {
    interface IPresenter {
        void request();
    }

    interface IView extends IBaseView<ActivityEvent> {

    }

    interface IModel {

    }
}
