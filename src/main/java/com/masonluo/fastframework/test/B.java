package com.masonluo.fastframework.test;


import com.masonluo.fastframework.beans.annotation.Configuration;
import com.masonluo.fastframework.core.annotation.Autowired;

/**
 * @author masonluo
 * @date 2020/6/29 12:00 PM
 */
@Configuration
public class B {

    private A a;

    public B(@Autowired A a) {
        this.a = a;
    }

    private String name;

    private String description;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
