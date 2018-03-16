package com.shileiyu.baseapp.common.net.cancel;


import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class DisCancelable implements Cancelable {
    private WeakReference<Disposable> weak;

    public DisCancelable(Disposable dis) {
        weak = new WeakReference<Disposable>(dis);
    }

    @Override
    public void cancel() {
        Disposable dis = weak.get();
        if (dis != null) {
            dis.dispose();
        }
    }
}
