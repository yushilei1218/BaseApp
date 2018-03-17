package com.shileiyu.baseapp.common.db;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author shilei.yu
 * @since on 2018/3/17.
 */

public class Run implements Runnable {
    ThreadPoolExecutor ex;

    public void setEx(ThreadPoolExecutor ex) {
        this.ex = ex;
    }

    @Override
    public void run() {
        Log.d("Task", "Thread =" + Thread.currentThread().getName() + " " + Thread.currentThread().hashCode());
        if (ex != null) {
            int activeCount = ex.getActiveCount();
            int poolSize = ex.getPoolSize();
            long taskCount = ex.getTaskCount();
            int corePoolSize = ex.getCorePoolSize();
            int largestPoolSize = ex.getLargestPoolSize();
            int maximumPoolSize = ex.getMaximumPoolSize();
            int size = ex.getQueue().size();
            String msg = "Queue size=" + size + " activeCount=" + activeCount + " poolSize=" + poolSize + " taskCount=" + taskCount
                    + " corePoolSize=" + corePoolSize + " largestPoolSize=" + largestPoolSize + " maximumPoolSize=" + maximumPoolSize;
            Log.d("Task", msg);
        }

    }
}
