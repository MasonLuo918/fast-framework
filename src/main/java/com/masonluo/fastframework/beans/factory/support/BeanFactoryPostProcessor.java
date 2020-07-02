package com.masonluo.fastframework.beans.factory.support;

import com.masonluo.fastframework.beans.factory.ConfigurableBeanFactory;

/**
 * @author masonluo
 * @date 2020/7/1 8:09 PM
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(ConfigurableBeanFactory beanFactory);
}
