package com.masonluo.fastframework.exception;

/**
 * @author masonluo
 * @date 2020/6/22 9:57 PM
 */
public class RepeatedlyBeanNameException extends RuntimeException {

    public RepeatedlyBeanNameException(){
        this("You must be has one bean name in the stereotype annotation");
    }

    public RepeatedlyBeanNameException(String msg){
        super(msg);
    }
}
