package com.masonluo.fastframework.beans.factory.support;

import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;

/**
 * @author masonluo
 * @date 2020/7/2 10:33 AM
 */
public interface BeanDefinitionRegsitryPostProcessor extends BeanFactoryPostProcessor {
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry);
}
