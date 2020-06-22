package com.masonluo.fastframework.core;

/**
 * 用于控制给一个类添加属性
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
