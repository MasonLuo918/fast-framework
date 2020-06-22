package com.masonluo.fastframework.exception;

/**
 * @author masonluo
 * @date 2020/6/22 9:57 PM
 */
public class BeanDefinitionExistException extends RuntimeException {

    public BeanDefinitionExistException(){
        this("Bean name has been register");
    }

    public BeanDefinitionExistException(String msg){
        super(msg);
    }
}
