package com.shileiyu.baseapp.common.util;

/**
 * @author shilei.yu
 * @date 2018/7/6
 */

public class Test2 {
    public static void main(String [] a){
        String url="https://10.2.2.203:8100/c/m/app/home/mi/test?platform=android&version=7.9.1&d=7893217d-050c-4f3d-9459-17c834831720&channel=zhaopin&v=7.91&uticket=5916dd6e3628471e86c9a0032528ab37&key=135486907212185&t=1530886098&e=dd46d5006eac1a4978058d70d2e3d1cb";
        url = url.replaceFirst("(https):*", "http:");
        System.out.print(url);
    }
}
