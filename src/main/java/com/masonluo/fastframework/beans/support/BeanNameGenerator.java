package com.masonluo.fastframework.beans.support;

import com.masonluo.fastframework.beans.factory.config.BeanDefinition;

/**
 * @author masonluo
 * @date 2020/6/22 6:01 PM
 */
public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
