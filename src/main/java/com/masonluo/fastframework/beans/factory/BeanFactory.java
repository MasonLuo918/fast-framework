package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.exception.BeanNotFoundException;
import com.masonluo.fastframework.exception.BeansException;

/**
 * Ioc ������ƣ����ڻ�ȡBean
 *
 * @author masonluo
 * @date 2020/6/20 6:46 PM
 */
public interface BeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * FactoryBean��ǰ׺
     * <p>
     * �����{@link BeanFactory#getBean(String) ȥ��ȡһ��Bean��ʱ�����ǰ׺Ϊ&{beanName}
     * �ͻ�ȡ���Bean��{@link FactoryBean}
     */
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * ���ݶ����beanName��ȡbaen
     * <p>
     * ���BeanNameû�ж������õĻ���Ĭ����Bean��Class��Simplename�����ҵ�һ����ĸСд
     */
    Object getBean(String beanName) throws BeanNotFoundException;

    /**
     * ����beanName��ȡһ��bean��requiredType�ǻ�ȡ���bean������
     * <p>
     * ������Ͳ����ϣ����׳��쳣
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

    /**
     * ����requiredType����ȡһ���������͵�bean
     * TODO ����һ��class-beanName map�����������������͵�bean
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * �ж�һ��bean�Ƿ��ǵ���
     */
    boolean isSingleton(String beanName);

    /**
     * �ж�һ��bean�Ƿ���ԭ��ģʽ
     */
    boolean isPrototype(String beanName);
}
