package com.shileiyu.baseapp.common.util;

import com.shileiyu.baseapp.common.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author shilei.yu
 * @since on 2018/3/17.
 */

public class ActivityTask {
    private ActivityTask() {
    }

    private static final List<ActivityWeakRef> TASK = new ArrayList<>();

    public static synchronized void addActivity(BaseActivity activity) {
        if (activity == null) {
            return;
        }
        ActivityWeakRef weak = new ActivityWeakRef(activity);
        boolean contains = TASK.contains(weak);
        if (!contains) {
            TASK.add(weak);
        }
    }

    public static synchronized void removeActivity(BaseActivity activity) {
        if (activity == null) {
            return;
        }
        int index = TASK.indexOf(new ActivityWeakRef(activity));
        if (index >= 0) {
            TASK.remove(index);
        }
    }

    public static synchronized BaseActivity getTopView() {
        ListIterator<ActivityWeakRef> iterator = TASK.listIterator(TASK.size() - 1);
        while (iterator.hasPrevious()) {
            BaseActivity ref = iterator.previous().get();
            if (ref != null) {
                return ref;
            }
        }
        return null;
    }

    private static final class ActivityWeakRef extends WeakReference<BaseActivity> {

        ActivityWeakRef(BaseActivity referent) {
            super(referent);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ActivityWeakRef) {
                BaseActivity ref = get();
                BaseActivity diff = ((ActivityWeakRef) obj).get();
                if (ref != null && diff != null) {
                    return ref.equals(diff);
                }
            }
            return super.equals(obj);
        }
    }

    public static synchronized List<BaseActivity> getList() {
        List<BaseActivity> list = new ArrayList<>();
        for (ActivityWeakRef w : TASK) {
            BaseActivity e = w.get();
            if (e != null) {
                list.add(e);
            }
        }
        return list;
    }
}
