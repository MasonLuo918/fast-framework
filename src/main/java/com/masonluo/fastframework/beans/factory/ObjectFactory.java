package com.masonluo.fastframework.beans.factory;

/**
 * @author masonluo
 * @date 2020/6/23 2:47 PM
 */
@FunctionalInterface
public interface ObjectFactory<T> {

    T getObject() throws RuntimeException;

}
