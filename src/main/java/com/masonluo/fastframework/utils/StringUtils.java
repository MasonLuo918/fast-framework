package com.masonluo.fastframework.utils;

/**
 * @author masonluo
 * @date 2020/6/22 6:05 PM
 */
public class StringUtils {
    public static String firstToLowerCase(String beanClassName){
        Assert.notBlank(beanClassName);
        char[] chars = beanClassName.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
