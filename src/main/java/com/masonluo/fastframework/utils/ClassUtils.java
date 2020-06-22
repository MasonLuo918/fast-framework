package com.masonluo.fastframework.utils;

/**
 * @author masonluo
 * @date 2020/6/22 6:13 PM
 */
public class ClassUtils {
    public static String getSimpleName(String fullName) {
        Assert.notBlank(fullName);
        String[] names = fullName.split("\\.");
        return names[names.length - 1];
    }

    public static void main(String[] args) {
        System.out.println(getSimpleName("com.masonluo.Annotation"));
    }
}
