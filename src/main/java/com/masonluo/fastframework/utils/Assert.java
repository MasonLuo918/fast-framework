package com.masonluo.fastframework.utils;

/**
 * @author masonluo
 * @date 2020/6/21 1:16 AM
 */
public class Assert {

    public static void notBlank(String name) {
        notBlank(name, "The parameter should not be empty");
    }

    public static void notBlank(String name, String msg) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "The parameter should not be null");
    }

    public static void notNull(Object object, String msg) {
        if (object == null) {
            throw new IllegalArgumentException(msg);
        }
    }
}
