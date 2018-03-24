package com.shileiyu.baseapp.common.pattern;

import java.util.HashMap;

/**
 * 享元模式
 *
 * @author shilei.yu
 * @since on 2018/3/24.
 */

public class FlyweightPattern {

    public static abstract class Flyweight {

    }

    public static class ConcreteFlyweight extends Flyweight {

    }

    public static class FlyweightFactory {

        private static HashMap<String, Flyweight> map = new HashMap<>();

        public static Flyweight get(String key) {
            Flyweight flyweight = map.get(key);
            if (flyweight == null) {
                flyweight = new ConcreteFlyweight();
                map.put(key, flyweight);
            }
            return flyweight;
        }
    }
}
