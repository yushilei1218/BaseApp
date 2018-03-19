package com.shileiyu.baseapp.common.db.rx;

import com.shileiyu.baseapp.common.bean.DaoSession;
import com.shileiyu.baseapp.common.db.DbClient;

import java.util.concurrent.Callable;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public abstract class DbCallable<T> implements Callable<T> {

    @Override
    public final T call() throws Exception {
        return get(DbClient.instance().getDaoSession());
    }

    protected abstract T get(DaoSession dao);
}
