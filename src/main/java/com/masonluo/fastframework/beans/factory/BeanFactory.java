package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.exception.BeanNotFoundException;

/**
 * Ioc 顶层设计，用于获取Bean
 *
 * @author masonluo
 * @date 2020/6/20 6:46 PM
 */
public interface BeanFactory {
    /**
     * 根据定义的beanName获取baen
     *
     * 如果BeanName没有额外设置的话，默认是Bean的Class的Simplename，并且第一个字母小写
     */
    Object getBean(String beanName) throws BeanNotFoundException;

    <T> T getBean(Class<T> requiredType);
}
