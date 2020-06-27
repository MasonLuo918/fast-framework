package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.exception.BeanNotFoundException;
import com.masonluo.fastframework.exception.BeansException;

/**
 * Ioc 顶层设计，用于获取Bean
 *
 * @author masonluo
 * @date 2020/6/20 6:46 PM
 */
public interface BeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * FactoryBean的前缀
     * <p>
     * 如果用{@link BeanFactory#getBean(String) 去获取一个Bean的时候，如果前缀为&{beanName}
     * 就获取这个Bean的{@link FactoryBean}
     */
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 根据定义的beanName获取baen
     * <p>
     * 如果BeanName没有额外设置的话，默认是Bean的Class的Simplename，并且第一个字母小写
     */
    Object getBean(String beanName) throws BeanNotFoundException;

    /**
     * 根据beanName获取一个bean，requiredType是获取这个bean的类型
     * <p>
     * 如果类型不符合，会抛出异常
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws BeansException;

    /**
     * 根据requiredType来获取一个符合类型的bean
     * TODO 构造一个class-beanName map，用来查找所需类型的bean
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 判断一个bean是否是单例
     */
    boolean isSingleton(String beanName);

    /**
     * 判断一个bean是否是原型模式
     */
    boolean isPrototype(String beanName);
}
