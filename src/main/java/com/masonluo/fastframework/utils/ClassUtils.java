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

    public static boolean equalIgnoreClassLoader(Class<?> clazz1, Class<?> clazz2) {
        if (clazz1 == null && clazz2 == null) {
            return true;
        }
        if (clazz1 == null || clazz2 == null) {
            return false;
        }
        return StringUtils.isEqual(clazz1.getName(), clazz2.getName());
    }

    public static boolean equal(Class<?> clazz1, Class<?> clazz2) {
        if (clazz1 == null && clazz2 == null) {
            return true;
        }
        if (clazz1 == null || clazz2 == null) {
            return false;
        }
        return clazz1.equals(clazz2);
    }

    public static void main(String[] args) {
        System.out.println(getSimpleName("com.masonluo.Annotation"));
    }
}
