package com.masonluo.fastframework.beans.factory;

/**
 * @author masonluo
 * @date 2020/6/23 1:53 PM
 */
public interface HierarchicalBeanFactory extends BeanFactory {

    BeanFactory getParentBeanFactory();

    /**
     * 查看是否在当前BeanFactory里面含有一个Bean，不包含其他父下文的
     */
    boolean containsInLocal(String name);
}
