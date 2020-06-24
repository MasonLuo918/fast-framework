package com.masonluo.fastframework.beans.factory.config;

import com.masonluo.fastframework.beans.MultiPropertyValues;
import com.masonluo.fastframework.core.AttributeAccessorSupport;

/**
 * @author masonluo
 * @date 2020/6/22 10:29 AM
 */
public abstract class AbstractBeanDefinition extends AttributeAccessorSupport implements BeanDefinition {

    private final static String SCOPE_DEFAULT = SCOPE.DEFAULT;

    private boolean lazyInit = false;

    private String scope = SCOPE_DEFAULT;

    private String beanName;

    private String beanClassName;

    private Class<?> beanClass;

    private boolean isPrimary = false;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBeanDefinition)) return false;

        AbstractBeanDefinition that = (AbstractBeanDefinition) o;

        if (isLazyInit() != that.isLazyInit()) return false;
        if (isPrimary() != that.isPrimary()) return false;
        if (!getScope().equals(that.getScope())) return false;
        if (!getBeanName().equals(that.getBeanName())) return false;
        if (!getBeanClassName().equals(that.getBeanClassName())) return false;
        if (!getBeanClass().equals(that.getBeanClass())) return false;
        if (!getPropertyValues().equals(that.getPropertyValues())) return false;
        return constructPropertyValues.equals(that.constructPropertyValues);
    }

    @Override
    public int hashCode() {
        int result = (isLazyInit() ? 1 : 0);
        result = 31 * result + getScope().hashCode();
        result = 31 * result + getBeanName().hashCode();
        result = 31 * result + getBeanClassName().hashCode();
        result = 31 * result + getBeanClass().hashCode();
        result = 31 * result + (isPrimary() ? 1 : 0);
        result = 31 * result + getPropertyValues().hashCode();
        result = 31 * result + constructPropertyValues.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AbstractBeanDefinition{" +
                "lazyInit=" + lazyInit +
                ", scope='" + scope + '\'' +
                ", beanName='" + beanName + '\'' +
                ", beanClassName='" + beanClassName + '\'' +
                ", beanClass=" + beanClass +
                ", isPrimary=" + isPrimary +
                ", propertyValues=" + propertyValues +
                ", constructPropertyValues=" + constructPropertyValues +
                '}';
    }
}
