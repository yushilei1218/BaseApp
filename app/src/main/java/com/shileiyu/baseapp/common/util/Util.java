package com.shileiyu.baseapp.common.util;

import android.widget.Toast;

import com.shileiyu.baseapp.common.base.BaseApp;

import java.util.Collection;

/**
 * @author shilei.yu
 * @since on 2018/3/16.
 */

public class Util {
    public static void toast(String msg) {
        Toast.makeText(BaseApp.appContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }
}
