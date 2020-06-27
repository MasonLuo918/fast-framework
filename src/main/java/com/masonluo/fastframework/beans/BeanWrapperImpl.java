package com.masonluo.fastframework.beans;

/**
 * @author masonluo
 * @date 2020/6/27 3:43 PM
 */
public class BeanWrapperImpl implements BeanWrapper {

    private Object wrapperInstance;

    private Class<?> wrapperClass;


    public BeanWrapperImpl(Object wrapperInstance, Class<?> wrapperClass) {
        this.wrapperClass = wrapperClass;
        this.wrapperInstance = wrapperInstance;
    }

    @Override
    public Object getWrapperInstance() {
        return wrapperInstance;
    }

    @Override
    public Class<?> getWrapperClass() {
        return wrapperClass;
    }
}
