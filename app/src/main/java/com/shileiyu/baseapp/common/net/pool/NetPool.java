package com.shileiyu.baseapp.common.net.pool;

import android.annotation.SuppressLint;

import com.shileiyu.baseapp.common.net.cancel.Cancelable;
import com.shileiyu.baseapp.common.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class NetPool {
    @SuppressLint("UseSparseArrays")
    private final static HashMap<Integer, List<Cancelable>> POOL = new HashMap<>();
    private final static HashMap<Cancelable, Integer> MATCH = new HashMap<>();

    public synchronized static void add(int taskId, Cancelable cancelable) {
        if (!POOL.containsKey(taskId)) {
            POOL.put(taskId, new ArrayList<Cancelable>());
        }
        POOL.get(taskId).add(cancelable);
        MATCH.put(cancelable, taskId);
    }

    public synchronized static void remove(Cancelable cancelable) {
        Integer integer = MATCH.get(cancelable);
        if (integer != null) {
            List<Cancelable> list = POOL.get(integer);
            if (!Util.isEmpty(list)) {
                list.remove(cancelable);
            }
            MATCH.remove(cancelable);
        }

    }

    public synchronized static void cancel(int taskId) {
        List<Cancelable> list = POOL.get(taskId);
        if (!Util.isEmpty(list)) {
            for (Cancelable c : list) {
                MATCH.remove(c);
                c.cancel();
            }
        }
        POOL.remove(taskId);
    }
}
