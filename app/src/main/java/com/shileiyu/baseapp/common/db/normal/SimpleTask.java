package com.shileiyu.baseapp.common.db.normal;

import com.shileiyu.baseapp.common.bean.DaoSession;
import com.shileiyu.baseapp.common.db.DbClient;

/**
 * @author shilei.yu
 * @since on 2018/3/17.
 */

public abstract class SimpleTask extends Run {

    protected abstract void call(DaoSession dao);

    @Override
    public final void run() {
        super.run();
        call(DbClient.instance().getDaoSession());
    }
}
