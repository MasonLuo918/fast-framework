package com.masonluo.fastframework.beans.factory.config;

import com.masonluo.fastframework.beans.MultiPropertyValues;
import com.masonluo.fastframework.core.AttributeAccessor;

/**
 * 用来描述一个Bean
 *
 * @author masonluo
 * @date 2020/6/20 11:41 PM
 */
public interface BeanDefinition extends AttributeAccessor {

    void setLazyInit(boolean lazyInit);

    /**
     * 判断是否懒加载，默认为true
     */
    boolean isLazyInit();

    /**
     * 获取scope
     */
    String getScope();

    void setScope(String scope);

    /**
     * 设置BeanName，也就是唯一id
     */
    String getBeanName();

    void setBeanName(String beanName);

    /**
     * bean的类全限定名
     */
    String getBeanClassName();

    void setBeanClassName(String beanClassName);

    /**
     * 是否优先注入，当自动注入的时候，
     * 如果发现多个同类型的，那么将自动注入优先的
     *
     * @return
     */
    boolean isPrimary();

    void setPrimary(boolean primary);

    /**
     * 获取预先设置好的字段属性值
     */
    MultiPropertyValues getPropertyValues();

    /**
     * 获取预先设置的构造函数参数值
     *
     * @return
     */
    MultiPropertyValues getConstructorPropertyValues();
}
