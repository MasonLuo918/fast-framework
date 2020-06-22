package com.masonluo.fastframework.utils;

/**
 * @author masonluo
 * @date 2020/6/21 1:16 AM
 */
public class Assert {
    public static void notBlank(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("The parameter should not be empty");
        }
    }

    public static void notNull(Object object){
        if (object == null){
            throw new IllegalArgumentException("The parameter should not be null");
        }
    }
}
