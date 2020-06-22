package com.masonluo.fastframework.core;

/**
 * ���ڿ��Ƹ�һ�����������
 *
 * @author masonluo
 * @date 2020/6/20 11:34 PM
 */
public interface AttributeAccessor {
    void setAttribute(String name, Object value);

    Object getAttribute(String name);

    Object removeAttribute(String name);

    boolean hasAttribute(String name);

    String[] attributeNames();
}
