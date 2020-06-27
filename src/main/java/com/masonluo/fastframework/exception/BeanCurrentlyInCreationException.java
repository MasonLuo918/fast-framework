package com.masonluo.fastframework.exception;

/**
 * @author masonluo
 * @date 2020/6/26 10:36 PM
 */
public class BeanCurrentlyInCreationException extends RuntimeException {
    private String name;

    public BeanCurrentlyInCreationException(String beanName){
        super("[" + beanName + "] is current in creation");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
