package com.shileiyu.baseapp.common.db;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.shileiyu.baseapp.common.base.BaseApp;
import com.shileiyu.baseapp.common.bean.BeanA;
import com.shileiyu.baseapp.common.bean.BeanADao;
import com.shileiyu.baseapp.common.bean.DaoMaster;
import com.shileiyu.baseapp.common.bean.DaoSession;
import com.shileiyu.baseapp.common.db.normal.ResultTask;
import com.shileiyu.baseapp.common.db.normal.SimpleTask;
import com.shileiyu.baseapp.common.db.rx.DbCallable;
import com.shileiyu.baseapp.common.db.rx.DbListCallable;

import org.greenrobot.greendao.database.Database;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * DbClient
 * <p>
 * 1：初始化 GreenDao 建立数据库DaoSession
 * <p>
 * 2：执行Task 已确保所有的数据库操作都在单一线程内顺序执行
 *
 * @author shilei.yu
 * @since on 2018/3/17.
 */

public class DbClient {
    private static final boolean ENCRYPTED = false;

    private static DbClient sClient;

    private final DaoSession mDaoSession;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "Db Thread #" + mCount.getAndIncrement());
        }
    };
    private final ThreadPoolExecutor mExecutor;

    private DbClient() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(BaseApp.appContext, ENCRYPTED ? "notes-db-encrypted" : "notes-db") {
            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                Logger.d("onUpgrade oldVersion=" + oldVersion + " newVersion=" + newVersion);
                //升级处理
                int tempOld = oldVersion;
                while (tempOld++ <= newVersion) {
                    switch (tempOld) {
                        case 2:
                            break;
                        case 3:
                            MigrationHelper.getInstance().migrate(db, BeanADao.class);
                            break;
                        default:
                            break;
                    }
                }
            }
        };
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

        mExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(128), THREAD_FACTORY);
        //核心线程超时也会销毁
        mExecutor.allowCoreThreadTimeOut(true);
    }

    public static synchronized DbClient instance() {
        if (sClient == null) {
            sClient = new DbClient();
        }
        return sClient;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public <T> void runTask(ResultTask<T> task) {
        task.setEx(mExecutor);
        mExecutor.submit(task);
    }

    public void runTask(SimpleTask task) {
        task.setEx(mExecutor);
        mExecutor.submit(task);
    }

    public <T> Flowable<T> flowable(DbCallable<T> callable) {
        return Flowable.fromCallable(callable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Flowable<List<T>> listFlowable(DbListCallable<T> callable) {
        return Flowable.fromCallable(callable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
