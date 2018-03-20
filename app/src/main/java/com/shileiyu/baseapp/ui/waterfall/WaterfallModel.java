package com.shileiyu.baseapp.ui.waterfall;

import android.os.SystemClock;
import android.util.Log;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.TwoTuple;
import com.shileiyu.baseapp.common.callback.ICallBack;
import com.shileiyu.baseapp.common.enums.DataState;
import com.shileiyu.baseapp.ui.waterfall.bean.WaterfallBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Callback;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class WaterfallModel implements WaterfallContract.IModel {
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
        Flowable
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
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WaterfallBean>>() {
                    @Override
                    public void accept(List<WaterfallBean> waterfallBeen) throws Exception {
                        if (isRefresh) {

                            boolean isEmpty = random.nextBoolean();
                            if (isEmpty) {
                                waterfallBeen.clear();
                                callback.call(new TwoTuple<>(waterfallBeen, DataState.EMPTY));
                                Log.d("WaterfallModel", "首页空集合 EMPTY");
                            } else {
                                boolean hasMore = random.nextBoolean();
                                callback.call(new TwoTuple<>(waterfallBeen, hasMore ? DataState.HAS_MORE : DataState.NO_MORE));
                                Log.d("WaterfallModel", "首页非空集合 hasMore=" + hasMore);
                            }
                        } else {
                            boolean hasMore = random.nextBoolean();
                            callback.call(new TwoTuple<>(waterfallBeen, hasMore ? DataState.HAS_MORE : DataState.NO_MORE));
                            Log.d("WaterfallModel", "非首页非空集合 hasMore=" + hasMore);
                        }
                    }
                });
    }
}
