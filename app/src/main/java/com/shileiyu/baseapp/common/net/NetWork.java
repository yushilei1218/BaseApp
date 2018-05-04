package com.shileiyu.baseapp.common.net;

import android.util.Log;

import com.shileiyu.baseapp.common.bean.DiscoveryBean;
import com.shileiyu.baseapp.common.bean.HttpResult;
import com.shileiyu.baseapp.common.bean.MyBean;
import com.shileiyu.baseapp.common.net.pool.CallPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class NetWork {
    public static Api sApi;
    private static final String BASE_URL = "http://mobile.ximalaya.com";

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(Client.instance().getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Retrofit wfit = new Retrofit.Builder()
                .baseUrl("https://www.sojson.com")
                .client(Client.instance().getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sApi = retrofit.create(Api.class);
        wApi = wfit.create(WeatherApi.class);
    }

    public static WeatherApi wApi;

    public interface Api {
        @GET("/mobile/discovery/v3/recommend/ts-1500624532898")
        Flowable<DiscoveryBean> getDiscovery();

        @GET("/mobile/discovery/v3/recommend/ts-1500624532898")
        Observable<DiscoveryBean> getODiscovery();

//        @GET("/mobile/discovery/v1/recommend/albums")
//        Call<RecommendBean> getRecommend(@Query("pageId") int pageId, @Query("pageSize") int pageSize);
    }

    public interface WeatherApi {
        @GET("/open/api/weather/json.shtml")
        Call<HttpResult<MyBean>> loadWeather(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather2(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather3(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather4(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather5(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather6(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather7(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather8(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather9(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather21(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather22(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather23(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather24(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather25(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather26(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather27(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather28(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather29(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather20(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather11(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather12(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather13(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather14(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather15(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather16(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather17(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather18(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather19(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather30(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather31(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather32(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather33(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather34(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather35(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather36(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather37(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather38(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather39(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather40(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather41(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather42(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather43(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather44(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather45(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather46(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather47(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather48(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather49(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather50(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather51(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather52(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather53(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather54(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather55(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather56(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather57(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather58(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather59(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather60(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather70(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather71(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather72(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather73(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather74(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather75(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather76(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather77(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather78(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather79(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather80(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather810(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather82(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather83(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather84(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather85(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather86(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather87(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather88(@Query("city") String city);

        @GET("/open/api/weather/json.shtml")
        Flowable<HttpResult<MyBean>> loadWeather89(@Query("city") String city);
    }

    public static WeatherApi getProxyApi(int taskId) {
        long a = System.currentTimeMillis();
        WeatherApi weatherApi = (WeatherApi) Proxy.newProxyInstance(wApi.getClass().getClassLoader(), new Class[]{WeatherApi.class}, new MyHandler(taskId));
        long b = System.currentTimeMillis();
        long c = b - a;
        Log.d("TimeA", c + "毫秒");
        return weatherApi;
    }

    static final class MyHandler implements InvocationHandler {
        final int taskId;

        MyHandler(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object invoke = method.invoke(wApi, args);
            if (invoke != null && invoke instanceof Call) {
                CallPool.addCall((Call) invoke, taskId);
            }
            return invoke;
        }
    }
}
