package com.shileiyu.baseapp.common.util;


import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import com.google.gson.Gson;
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

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }


    public static void main(String[] a) {
        Spanned spanned = Html.fromHtml(
                "<p>任职要求：</p><p>1.刷卡机倒垃圾</p><p>2.倨傲四大剌史莱克</p><p>3.8273u8923</p><p><br/></p><p>智能要求：</p><p>2.骄傲看你的</p><p>4.脑袋快</p>");

        String s = spanned.toString();
        System.out.print("[" + s + "]");

    }
}
