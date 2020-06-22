package com.masonluo.fastframework.beans.support;

/**
 * 单例注册机， 为什么没有原型的注册机呢？
 * <p>
 * 因为原型在Ioc容器中并不进行缓存
 *
 * @author masonluo
 * @date 2020/6/20 6:50 PM
 */
public interface SingletonBeanRegistry {

    /**
     * 注册一个单例
     */
    void registerSingleton(String beanName, Object singletonBean);

    /**
     * 通过beanName获取一个单例
     * <p>
     * beanName是预定义的beanName，或者是默认由驼峰规则，并且首字母小写组成的beanName
     */
    Object getSingleton(String beanName);

    /**
     * 判断是否存在一个单例在缓存中
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取单例的个数
     */
    int getSingletonCount();

    /**
     * 获取所有单例的名字
     */
    String[] getSingletonNames();
}
