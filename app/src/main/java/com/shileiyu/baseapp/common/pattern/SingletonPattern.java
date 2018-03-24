package com.shileiyu.baseapp.common.pattern;

/**
 * 单例
 *
 * @author shilei.yu
 * @since on 2018/3/24.
 */

public class SingletonPattern {

    public static class Singleton {
        private Singleton() {
        }

        private static Singleton instance;

        public static synchronized Singleton instance() {
            if (instance == null) {
                instance = new Singleton();
            }
            return instance;
        }
    }
}
