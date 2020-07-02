package com.masonluo.fastframework.beans.factory.config;

import com.masonluo.fastframework.beans.MultiPropertyValues;
import com.masonluo.fastframework.beans.factory.AutowiredCapableBeanFactory;

/**
 * @author masonluo
 * @date 2020/6/22 10:53 AM
 */
public class GenericBeanDefinition extends AbstractBeanDefinition {

    private int autowireMode = AutowiredCapableBeanFactory.AUTOWIRE_BY_TYPE;

    public GenericBeanDefinition(){
        super();
    }

    public GenericBeanDefinition(Class<?> clazz){
        this(clazz, SCOPE.DEFAULT, AutowiredCapableBeanFactory.AUTOWIRE_NO);
    }

    public GenericBeanDefinition(Class<?> clazz, String scope){
        this(clazz, scope, AutowiredCapableBeanFactory.AUTOWIRE_NO);
    }


    public GenericBeanDefinition(Class<?> clazz, int autowireMode){
        this(clazz, SCOPE.DEFAULT, autowireMode);
    }

    public GenericBeanDefinition(MultiPropertyValues constructorArgs, MultiPropertyValues propertyValues){
        super(constructorArgs, propertyValues);
    }

    public GenericBeanDefinition(Class<?> clazz, String scope, int autowireMode){
        setBeanClass(clazz);
        setScope(scope);
        setAutowireMode(autowireMode);
    }

    public int getAutowireMode() {
        return autowireMode;
    }

    public void setAutowireMode(int autowireMode) {
        this.autowireMode = autowireMode;
    }
}
