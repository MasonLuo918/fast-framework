package com.masonluo.fastframework.test;

import com.masonluo.fastframework.core.annotation.Autowired;

/**
 * @author masonluo
 * @date 2020/6/29 12:01 PM
 */
public class C {

    @Autowired
    private D d;

    private String name;

    private String description;

    public C() {
    }

    public C(D d, String name, String description) {
        this.d = d;
        this.name = name;
        this.description = description;
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
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
