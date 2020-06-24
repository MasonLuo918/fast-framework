package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.exception.BeanNotFoundException;

/**
 * Ioc ������ƣ����ڻ�ȡBean
 *
 * @author masonluo
 * @date 2020/6/20 6:46 PM
 */
public interface BeanFactory {

    String BEAN_FACTORY_PREFIX = "&";

    /**
     * ���ݶ����beanName��ȡbaen
     * <p>
     * ���BeanNameû�ж������õĻ���Ĭ����Bean��Class��Simplename�����ҵ�һ����ĸСд
     */
    Object getBean(String beanName) throws BeanNotFoundException;

    <T> T getBean(Class<T> requiredType);

    boolean isSingleton(String beanName);

    boolean isPrototype(String beanName);
}
