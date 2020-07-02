package com.masonluo.fastframework.test;

import com.masonluo.fastframework.beans.stereotype.Controller;
import com.masonluo.fastframework.core.annotation.Autowired;

/**
 * @author masonluo
 * @date 2020/6/29 12:01 PM
 */
@Controller
public class D {
    @Autowired
    private C c;

    private String name;

    private String description;

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
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
