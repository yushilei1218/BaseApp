package com.shileiyu.baseapp.common.util;

/**
 * @author shilei.yu
 * @since on 2018/3/19.
 */

public class Test {
    private static int index = 0;

    private static Thread thread = Thread.currentThread();

    public static synchronized void test() {

        index++;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String msg = Thread.currentThread().getName() + " " + index;
        System.out.println(msg);
    }

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                test();
            }
        }).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test();
    }
}
