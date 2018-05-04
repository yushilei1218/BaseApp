package com.shileiyu.baseapp.ui.home;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shileiyu.baseapp.R;
import com.shileiyu.baseapp.common.bean.HttpResult;
import com.shileiyu.baseapp.common.bean.MyBean;
import com.shileiyu.baseapp.common.mvp.BaseMvpActivity;
import com.shileiyu.baseapp.common.net.NetWork;
import com.shileiyu.baseapp.common.net.pool.CallPool;
import com.shileiyu.baseapp.common.util.Util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author shilei.yu
 */
public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeConstract.IView {

    @BindView(R.id.get_weather)
    Button mGetWeather;
    @BindView(R.id.get_content)
    TextView mTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }


    @OnClick({R.id.get_weather,
            R.id.get_weather_test1,
            R.id.get_weather_test2,
            R.id.get_weather_subscriber,
            R.id.get_weather_call,
            R.id.get_weather_rx})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.get_weather:
                getWeather();
                break;
            case R.id.get_weather_rx:
                getWeatherRx();
                break;
            case R.id.get_weather_test1:
                test1();
                break;
            case R.id.get_weather_test2:
                test2();
                break;
            case R.id.get_weather_subscriber:
                test3();
            case R.id.get_weather_call:
                test4();
                break;
            default:
                break;
        }

    }

    private void test4() {
        NetWork.getProxyApi(getTaskId()).loadWeather("北京").enqueue(new Callback<HttpResult<MyBean>>() {
            @Override
            public void onResponse(Call<HttpResult<MyBean>> call, Response<HttpResult<MyBean>> response) {
                CallPool.removeCall(call);
                Log.d(getTAG(), "onResponse");
                mTv.setText(Util.toJson(response.body()));
            }

            @Override
            public void onFailure(Call<HttpResult<MyBean>> call, Throwable t) {
                CallPool.removeCall(call);
                t.printStackTrace();
                Log.d(getTAG(), "onFailure " + t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        CallPool.cancelCall(getTaskId());
        super.onDestroy();
    }

    private void test3() {
        NetWork.wApi.loadWeather2("北京")
                .map(new Function<HttpResult<MyBean>, HttpResult<MyBean>>() {
                    @Override
                    public HttpResult<MyBean> apply(HttpResult<MyBean> myBeanHttpResult) throws Exception {
                        SystemClock.sleep(5000);
                        Log.d(getTAG(), "apply " + Thread.currentThread().getName());
                        return myBeanHttpResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getRxLifecycle().<HttpResult<MyBean>>bindToLifecycle())
                .subscribe(new Subscriber<HttpResult<MyBean>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                        Log.d(getTAG(), "onSubscribe");
                    }

                    @Override
                    public void onNext(HttpResult<MyBean> myBeanHttpResult) {
                        Log.d(getTAG(), "onNext");
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(getTAG(), "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(getTAG(), "onComplete");
                    }
                });
    }

    private void test2() {
        Flowable
                .fromCallable(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        SystemClock.sleep(5000);
                        Log.d(getTAG(), Thread.currentThread().getName());
                        return 100;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(getRxLifecycle().<Integer>bindToLifecycle())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                        Log.d(getTAG(), "onSubscribe ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(getTAG(), "onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(getTAG(), "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(getTAG(), "onComplete");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void test1() {
        Flowable
                .fromCallable(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        SystemClock.sleep(5000);
                        return 100;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(getRxLifecycle().<Integer>bindToLifecycle())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(getTAG(), "accept " + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(getTAG(), "accept throwable " + throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(getTAG(), "accept complete ");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getWeatherRx() {
        NetWork.wApi.loadWeather2("北京")
                .map(new Function<HttpResult<MyBean>, HttpResult<MyBean>>() {
                    @Override
                    public HttpResult<MyBean> apply(HttpResult<MyBean> myBeanHttpResult) throws Exception {
                        SystemClock.sleep(5000);
                        Log.d(getTAG(), "apply " + Thread.currentThread().getName());
                        return myBeanHttpResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getRxLifecycle().<HttpResult<MyBean>>bindToLifecycle())
                .subscribe(new Consumer<HttpResult<MyBean>>() {
                    @Override
                    public void accept(HttpResult<MyBean> myBeanHttpResult) throws Exception {
                        showToast("accept");
                        Log.d(getTAG(), "accept " + Thread.currentThread().getName());
                        mTv.setText(Util.toJson(myBeanHttpResult));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showToast("error");
                        Log.d(getTAG(), "error " + Thread.currentThread().getName());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        showToast("Action");
                        Log.d(getTAG(), "Action " + Thread.currentThread().getName());
                    }
                });
    }

    private void getWeather() {
        NetWork.wApi.loadWeather("北京").enqueue(new Callback<HttpResult<MyBean>>() {
            @Override
            public void onResponse(Call<HttpResult<MyBean>> call, Response<HttpResult<MyBean>> response) {
                mTv.setText(Util.toJson(response.body()));
            }

            @Override
            public void onFailure(Call<HttpResult<MyBean>> call, Throwable t) {
                showToast("error");
            }
        });
    }

    @Override
    public String getTAG() {
        return "RxJava";
    }
}
