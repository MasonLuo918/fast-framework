package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.factory.config.GenericBeanDefinition;

/**
 * @author masonluo
 * @date 2020/6/27 1:54 PM
 */
public class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    protected Object createBean(String beanName, GenericBeanDefinition beanDefinition) {
        return null;
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    protected String getPrimaryBeanNameForClass(Class<?> clazz) {
        return null;
    }
}
