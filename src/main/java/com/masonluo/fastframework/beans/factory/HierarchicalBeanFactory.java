package com.masonluo.fastframework.beans.factory;

/**
 * @author masonluo
 * @date 2020/6/23 1:53 PM
 */
public interface HierarchicalBeanFactory extends BeanFactory {

    BeanFactory getParentBeanFactory();

    /**
     * �鿴�Ƿ��ڵ�ǰBeanFactory���溬��һ��Bean�����������������ĵ�
     */
    boolean containsInLocal(String name);
}
