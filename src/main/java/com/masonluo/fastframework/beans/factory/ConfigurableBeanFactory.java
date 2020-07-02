package com.masonluo.fastframework.beans.factory;

/**
 * @author masonluo
 * @date 2020/6/24 5:04 PM
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {
    void setParentBeanFactory(BeanFactory parent);
}