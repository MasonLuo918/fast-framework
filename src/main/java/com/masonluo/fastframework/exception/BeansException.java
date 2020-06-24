package com.masonluo.fastframework.exception;

/**
 * @author masonluo
 * @date 2020/6/24 6:17 PM
 */
public class BeansException extends RuntimeException {
    public BeansException(){
        this("There is a error happend in create bean");
    }

    public BeansException(String msg){
        super(msg);
    }
}
