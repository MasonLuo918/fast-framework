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
     * ��ü�ֵ������
     */
    PropertyValue[] getPropertyValues();

    /**
     * �����Ӧ���ֵļ�ֵ��
     */
    PropertyValue getPropertyValue(String name);

    /**
     * �ж��Ƿ������Ӧname�ļ�ֵ��
     */
    boolean contains(String name);

    /**
     * �Ƿ�Ϊ��
     */
    boolean isEmpty();

    /**
     * ����һ����ֵ��
     */
    void addPropertyValue(PropertyValue propertyValue);

    /**
     * �Ƴ�ĳһ����ֵ�ԣ����ҽ��䷵��
     */
    PropertyValue removePropertyValue(String name);

    /**
     * ��ü�ֵ�Ե�����
     */
    int propertyValueCount();

}
