package com.shileiyu.baseapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.orhanobut.logger.Logger;
import com.shileiyu.baseapp.common.bean.BeanA;


/**
 * @author shilei.yu
 * @since on 2018/4/4.
 */

public class MyViewModel extends ViewModel {
    MutableLiveData<BeanA> mBeans;
    private int index = 0;

    public MyViewModel() {
        Logger.d("MyViewModel 构造函数" + this);
    }

    @Override
    protected void onCleared() {
        Logger.d("MyViewModel onCleared" + this);
    }

    public LiveData<BeanA> getBeanALiveData() {
        if (mBeans == null) {
            mBeans = new MutableLiveData<>();
            loadBeanAs();
        }
        return mBeans;
    }

    public void loadBeanAs() {
        index++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BeanA value = new BeanA();
                value.setId(12L);
                value.setName("测试BeanA " + index);
                value.setTime(System.currentTimeMillis());

                mBeans.postValue(value);
            }
        }, 1000);
    }
}
