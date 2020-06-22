package com.masonluo.fastframework.beans;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * @author masonluo
 * @date 2020/6/21 12:42 AM
 */
public interface PropertyValues extends Iterable<PropertyValue> {


    @Override
    default Iterator<PropertyValue> iterator() {
        return Arrays.asList(getPropertyValues()).iterator();
    }

    @Override
    default void forEach(Consumer<? super PropertyValue> action) {

    }

    @Override
    default Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(getPropertyValues(), 0);
    }

    /**
     * 获得键值对数组
     */
    PropertyValue[] getPropertyValues();

    /**
     * 获得相应名字的键值对
     */
    PropertyValue getPropertyValue(String name);

    /**
     * 判断是否包含相应name的键值对
     */
    boolean contains(String name);

    /**
     * 是否为空
     */
    boolean isEmpty();

    /**
     * 增添一个键值对
     */
    void addPropertyValue(PropertyValue propertyValue);

    /**
     * 移除某一个键值对，并且将其返回
     */
    PropertyValue removePropertyValue(String name);

    /**
     * 获得键值对的数量
     */
    int propertyValueCount();

}
