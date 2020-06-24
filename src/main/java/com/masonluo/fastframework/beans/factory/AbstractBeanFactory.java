package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.beans.support.FactoryBeanSupportRegister;
import com.masonluo.fastframework.exception.BeanNotFoundException;

/**
 * @author masonluo
 * @date 2020/6/24 5:10 PM
 */
public abstract class AbstractBeanFactory extends FactoryBeanSupportRegister implements ConfigurableBeanFactory {

    private BeanFactory parent;

    @Override
    public void setParentBeanFactory(BeanFactory parent) {
        this.parent = parent;
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return parent;
    }

    @Override
    public boolean containsInLocal(String name) {
        return containsSingleton(name) || containsBeanDefinition(name);
    }

    @Override
    public Object getBean(String beanName) throws BeanNotFoundException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return null;
    }

    @Override
    public boolean isSingleton(String beanName) {
        return false;
    }

    @Override
    public boolean isPrototype(String beanName) {
        return false;
    }

    public abstract boolean containsBeanDefinition(String beanName);
}
