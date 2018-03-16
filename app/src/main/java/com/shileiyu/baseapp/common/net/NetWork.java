package com.shileiyu.baseapp.common.net;

import com.shileiyu.baseapp.common.bean.DiscoveryBean;

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
        sApi = retrofit.create(Api.class);
    }

    public interface Api {
        @GET("/mobile/discovery/v3/recommend/ts-1500624532898")
        Flowable<DiscoveryBean> getDiscovery();

        @GET("/mobile/discovery/v3/recommend/ts-1500624532898")
        Observable<DiscoveryBean> getODiscovery();

//        @GET("/mobile/discovery/v1/recommend/albums")
//        Call<RecommendBean> getRecommend(@Query("pageId") int pageId, @Query("pageSize") int pageSize);
    }
}
