package com.masonluo.fastframework.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author masonluo
 * @date 2020/6/22 10:31 AM
 */
public class AttributeAccessorSupport implements AttributeAccessor {

    private Map<String, Object> attributes = new HashMap<>();

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    @Override
    public String[] attributeNames() {
        return attributes.keySet().toArray(new String[0]);
    }
}
