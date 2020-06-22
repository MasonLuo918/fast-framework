package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.exception.BeanNotFoundException;

/**
 * Ioc ������ƣ����ڻ�ȡBean
 *
 * @author masonluo
 * @date 2020/6/20 6:46 PM
 */
public interface BeanFactory {
    /**
     * ���ݶ����beanName��ȡbaen
     *
     * ���BeanNameû�ж������õĻ���Ĭ����Bean��Class��Simplename�����ҵ�һ����ĸСд
     */
    Object getBean(String beanName) throws BeanNotFoundException;

    <T> T getBean(Class<T> requiredType);
}
