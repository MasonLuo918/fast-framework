package com.masonluo.fastframework.test;

import com.masonluo.fastframework.beans.annotation.ComponentScan;
import com.masonluo.fastframework.beans.stereotype.Component;

/**
 * @author masonluo
 * @date 2020/6/29 11:57 AM
 */
@Component
public class A {

    private String name;

    private String description;

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
