package com.masonluo.fastframework.beans.factory;

/**
 * @author masonluo
 * @date 2020/6/27 2:18 PM
 */
public interface AutowiredCapableBeanFactory extends BeanFactory {

    int AUTOWIRE_NO = 0;

    int AUTOWIRE_BY_NAME = 1;

    int AUTOWIRE_BY_TYPE = 2;

    <T> T createBean(Class<T> beanType);
}
