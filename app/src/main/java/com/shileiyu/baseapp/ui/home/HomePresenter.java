package com.shileiyu.baseapp.ui.home;

import com.shileiyu.baseapp.common.bean.DiscoveryBean;
import com.shileiyu.baseapp.common.mvp.BasePresenter;
import com.shileiyu.baseapp.common.net.observer.NetObserver;
import com.shileiyu.baseapp.common.net.observer.NetSubscriber;
import com.shileiyu.baseapp.common.net.NetWork;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class HomePresenter extends BasePresenter<HomeConstract.IView> implements HomeConstract.IPresenter {
    HomePresenter(HomeConstract.IView view) {
        super(view);
    }

    @Override
    public void request() {

        Flowable<DiscoveryBean> discovery = NetWork.sApi.getDiscovery();
        Observable<DiscoveryBean> oDiscovery = NetWork.sApi.getODiscovery();

        discovery.subscribe(new NetSubscriber<DiscoveryBean>(taskId) {
            @Override
            public void onFailed(Throwable t) {

            }

            @Override
            public void onNext(DiscoveryBean discoveryBean) {

            }
        });
        oDiscovery.subscribe(new NetObserver<DiscoveryBean>(taskId) {
            @Override
            public void onFailed(Throwable e) {

            }

            @Override
            public void onNext(@NonNull DiscoveryBean discoveryBean) {

            }
        });
    }
}
