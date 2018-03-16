package com.shileiyu.baseapp.common.net.cancel;

import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class SubCancelable implements Cancelable {
    private WeakReference<Subscription> weak;

    public SubCancelable(Subscription sub) {
        weak = new WeakReference<>(sub);
    }

    @Override
    public void cancel() {
        Subscription sub = weak.get();
        if (sub != null) {
            sub.cancel();
        }
    }
}
