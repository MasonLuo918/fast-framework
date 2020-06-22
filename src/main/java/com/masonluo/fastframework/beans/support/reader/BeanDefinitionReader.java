package com.masonluo.fastframework.beans.support.reader;

import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;

/**
 * @author masonluo
 * @date 2020/6/22 11:28 AM
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    int loadBeanDefinition(String resourceLocation) throws ClassNotFoundException;

    int loadBeanDefinition(String... resourceLocation) throws ClassNotFoundException;
}
