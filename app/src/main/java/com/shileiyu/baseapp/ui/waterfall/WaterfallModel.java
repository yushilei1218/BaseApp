package com.shileiyu.baseapp.ui.waterfall;

import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.common.net.cancel.DisCancelable;
import com.shileiyu.baseapp.common.net.observer.NetSubscriber;
import com.shileiyu.baseapp.common.net.pool.NetPool;
import com.shileiyu.baseapp.ui.waterfall.bean.ADBean;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Callback;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class WaterfallModel implements WaterfallContract.IModel {
    private final int taskId;
    private LifecycleProvider<ActivityEvent> lifeCycle;
    private static final String TAG = "WaterfallModel";

    public WaterfallModel(int taskId, LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.taskId = taskId;
        lifeCycle = lifecycleProvider;
    }

    private int index = 0;
    private int[] mRids = {R.mipmap.ic_img_1
            , R.mipmap.ic_img_2
            , R.mipmap.ic_img_3
            , R.mipmap.ic_img_4
            , R.mipmap.ic_img_5
            , R.mipmap.ic_img_6
            , R.mipmap.ic_img_7
            , R.mipmap.ic_img_8
            , R.mipmap.ic_img_9
            , R.mipmap.ic_img_10
            , R.mipmap.ic_img_11
            , R.mipmap.ic_img_12
            , R.mipmap.ic_img_13
            , R.mipmap.ic_img_14
            , R.mipmap.ic_img_15
            , R.mipmap.ic_img_16
            , R.mipmap.ic_img_17
            , R.mipmap.ic_img_18
            , R.mipmap.ic_img_19
            , R.mipmap.ic_img_20
            , R.mipmap.ic_img_21
    };


    @Override
    public void load(final boolean isRefresh, final ICallBack<TwoTuple<List<WaterfallBean>, DataState>> callback) {
        index = isRefresh ? index = 0 : index++;
        if (isRefresh) {
            data.clear();
        }
        final Random random = new Random();
        Flowable<List<WaterfallBean>> flowable = Flowable
                .fromCallable(new Callable<List<WaterfallBean>>() {
                    @Override
                    public List<WaterfallBean> call() throws Exception {
                        SystemClock.sleep(3000);
                        for (int i = 0; i < 20; i++) {
                            int next = random.nextInt(mRids.length);
                            data.add(new WaterfallBean(mRids[next]));
                        }
                        return data;
                    }
                });

        compose(flowable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<WaterfallBean>>() {
                            @Override
                            public void accept(List<WaterfallBean> waterfallBeen) throws Exception {
                                Logger.e("onNext accept");
                                if (isRefresh) {

                                    int tag = random.nextInt(10);
                                    if (tag >= 7) {
                                        waterfallBeen.clear();
                                        callback.call(new TwoTuple<>(waterfallBeen, DataState.EMPTY));
                                        Log.e("WaterfallModel", "首页空集合 EMPTY");
                                    } else {
                                        boolean hasMore = random.nextBoolean();
                                        callback.call(new TwoTuple<>(waterfallBeen, hasMore ? DataState.HAS_MORE : DataState.NO_MORE));
                                        Log.e("WaterfallModel", "首页非空集合 hasMore=" + hasMore);
                                    }
                                } else {
                                    boolean hasMore = random.nextBoolean();
                                    callback.call(new TwoTuple<>(waterfallBeen, hasMore ? DataState.HAS_MORE : DataState.NO_MORE));
                                    Log.e("WaterfallModel", "非首页非空集合 hasMore=" + hasMore);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Logger.e("onError accept");
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                Logger.e("onComplete run");
                            }
                        });

    }

    @Override
    public void loadAd(ICallBack<List<TwoTuple<ADBean, ADBean>>> callBack) {
        List<TwoTuple<ADBean, ADBean>> data = new ArrayList<>();
        data.add(new TwoTuple<>(new ADBean("测试广告111"), new ADBean("测试广告222")));
        data.add(new TwoTuple<>(new ADBean("测试广告333"), new ADBean("测试广告444")));
        data.add(new TwoTuple<>(new ADBean("测试广告555"), new ADBean("测试广告666")));
        data.add(new TwoTuple<>(new ADBean("测试广告777"), (ADBean)null));
        callBack.call(data);
    }

    private <T> Flowable<T> compose(Flowable<T> real) {
        return real.compose(lifeCycle.<T>bindUntilEvent(ActivityEvent.DESTROY));
    }


//new NetSubscriber<List<WaterfallBean>>(taskId) {
//        @Override
//        public void onFailed(Throwable t) {
//            Logger.e("onFailed");
//        }
//
//        @Override
//        public void onNext(List<WaterfallBean> waterfallBeen) {
//            if (isRefresh) {
//
//                boolean isEmpty = random.nextBoolean();
//                if (isEmpty) {
//                    waterfallBeen.clear();
//                    callback.call(new TwoTuple<>(waterfallBeen, DataState.EMPTY));
//                    Logger.e("首页空集合 EMPTY");
//                } else {
//                    boolean hasMore = random.nextBoolean();
//                    callback.call(new TwoTuple<>(waterfallBeen, hasMore ? DataState.HAS_MORE : DataState.NO_MORE));
//                    Logger.e("首页非空集合 hasMore=" + hasMore);
//                }
//            } else {
//                boolean hasMore = random.nextBoolean();
//                callback.call(new TwoTuple<>(waterfallBeen, hasMore ? DataState.HAS_MORE : DataState.NO_MORE));
//                Logger.e("非首页非空集合 hasMore=" + hasMore);
//            }
//        }
//    }
//
}
