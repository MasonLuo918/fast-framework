package com.masonluo.fastframework.beans.factory;

/**
 * @author masonluo
 * @date 2020/6/24 4:31 PM
 */
public interface FactoryBean<T> {

    Class<?> getObjectType();

    boolean isSingleton();

    T getObject();
}
