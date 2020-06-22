package com.masonluo.fastframework.exception;

/**
 * @author masonluo
 * @date 2020/6/20 6:46 PM
 */
public class BeanNotFoundException extends Exception {

    public BeanNotFoundException() {
        this("The bean dose not found in the bean factory");
    }

    public BeanNotFoundException(String msg) {
        super(msg);
    }
}
