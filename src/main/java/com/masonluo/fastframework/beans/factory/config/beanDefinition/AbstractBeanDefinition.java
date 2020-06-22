package com.masonluo.fastframework.beans.factory.config.beanDefinition;

import com.masonluo.fastframework.beans.MultiPropertyValues;
import com.masonluo.fastframework.core.AttributeAccessorSupport;

/**
 * @author masonluo
 * @date 2020/6/22 10:29 AM
 */
public abstract class AbstractBeanDefinition extends AttributeAccessorSupport implements BeanDefinition {

    private final static String SCOPE_DEFAULT = SCOPE_SINGLETON;

    private boolean lazyInit = false;

    private String scope = SCOPE_DEFAULT;

    private String beanName;

    private String beanClassName;

    private Class<?> beanClass;

    private boolean isPrimary;

    private MultiPropertyValues propertyValues;

    private MultiPropertyValues constructPropertyValues;

    public AbstractBeanDefinition() {
        this(null, null);
    }

    public AbstractBeanDefinition(MultiPropertyValues constructPropertyValues, MultiPropertyValues propertyValues) {
        this.propertyValues = propertyValues;
        this.constructPropertyValues = constructPropertyValues;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public boolean isLazyInit() {
        return lazyInit;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getBeanClassName() {
        return beanClassName;
    }

    @Override
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public boolean isPrimary() {
        return isPrimary;
    }

    @Override
    public void setPrimary(boolean primary) {
        this.isPrimary = primary;
    }

    @Override
    public MultiPropertyValues getPropertyValues() {
        return propertyValues;
    }

    @Override
    public MultiPropertyValues getConstructorPropertyValues() {
        return constructPropertyValues;
    }
}
