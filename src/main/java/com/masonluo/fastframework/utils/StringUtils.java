package com.masonluo.fastframework.utils;

/**
 * @author masonluo
 * @date 2020/6/22 6:05 PM
 */
public class StringUtils {

    public static boolean hasText(String string) {
        return !isBlank(string);
    }

    public static boolean isBlank(String str) {
        return (str == null || str.length() == 0);
    }

    public static String firstToLowerCase(String beanClassName) {
        Assert.notBlank(beanClassName);
        char[] chars = beanClassName.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }

    public static boolean isEqual(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equals(str2);
    }

    public static String packageToPath(String pack) {
        if (pack == null || pack.length() == 0) {
            return null;
        }
        return pack.replaceAll("\\.", "/");
    }

    public static String append(String... strs){
        StringBuilder builder = new StringBuilder();
        for (String str : strs){
            builder.append(str);
        }
        return builder.toString();
    }
}
