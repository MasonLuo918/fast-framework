package com.masonluo.fastframework.utils;

import java.lang.reflect.Constructor;

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

    public static void hasLength(Constructor[] constructors){
        hasLength(constructors, "The array parameter must not be empty");
    }

    public static void hasLength(Constructor[] constructors, String msg) {
        if (constructors == null || constructors.length == 0){
            throw new IllegalArgumentException(msg);
        }
    }
}
