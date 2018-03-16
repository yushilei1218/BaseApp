package com.shileiyu.baseapp.ui.home;

import com.shileiyu.baseapp.common.bean.DiscoveryBean;
import com.shileiyu.baseapp.common.mvp.BasePresenter;
import com.shileiyu.baseapp.common.net.NetWork;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
        Disposable subscribe1 = oDiscovery.subscribe(new Consumer<DiscoveryBean>() {
            @Override
            public void accept(DiscoveryBean discoveryBean) throws Exception {

            }
        });
        Disposable subscribe = discovery.subscribe(new Consumer<DiscoveryBean>() {
            @Override
            public void accept(DiscoveryBean discoveryBean) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }
}
