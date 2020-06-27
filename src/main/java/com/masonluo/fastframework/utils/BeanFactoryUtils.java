package com.masonluo.fastframework.utils;

import com.masonluo.fastframework.beans.factory.BeanFactory;

/**
 * @author masonluo
 * @date 2020/6/27 1:23 PM
 */
public class BeanFactoryUtils {
    private final static String FACTORY_BEAN_PREFIX = BeanFactory.FACTORY_BEAN_PREFIX;

    public static boolean isFactoryBeanReference(String name) {
        return name != null && name.startsWith("&");
    }
}
