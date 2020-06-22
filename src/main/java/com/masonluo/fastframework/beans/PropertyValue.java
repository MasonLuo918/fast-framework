package com.masonluo.fastframework.beans;

import java.util.LinkedList;
import java.util.List;

/**
 * @author masonluo
 * @date 2020/6/21 12:05 AM
 */
public class PropertyValue {

    private String name;


    private Object value;

    public PropertyValue() {
    }

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
