package com.masonluo.fastframework.beans.support;

import com.masonluo.fastframework.beans.factory.config.BeanDefinition;

/**
 * @author masonluo
 * @date 2020/6/22 10:55 AM
 */
public interface BeanDefinitionRegistry {

    void registryBeanDefinition(String beanName, BeanDefinition definition);

    void registerClassToBeanName(String className, String beanName);

    BeanDefinition removeBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    boolean containsBeanDefinition(String beanName);

    String getPrimaryBeanNameForClass(Class<?> clazz);
}
