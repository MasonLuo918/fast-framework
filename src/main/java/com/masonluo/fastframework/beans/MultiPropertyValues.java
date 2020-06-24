package com.masonluo.fastframework.beans;

import com.masonluo.fastframework.utils.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author masonluo
 * @date 2020/6/21 1:05 AM
 */
public class MultiPropertyValues implements PropertyValues {

    private List<PropertyValue> propertyValueList;

    public MultiPropertyValues() {
        propertyValueList = new ArrayList<>(0);
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String name) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(name)) {
                return propertyValue;
            }
        }
        return null;
    }

    @Override
    public boolean contains(String name) {
        return getPropertyValue(name) == null;
    }

    @Override
    public boolean isEmpty() {
        return propertyValueList.isEmpty();
    }

    @Override
    public void addPropertyValue(PropertyValue propertyValue) {
        Assert.notBlank(propertyValue.getName());
        propertyValueList.add(propertyValue);
    }

    @Override
    public PropertyValue removePropertyValue(String name) {
        PropertyValue propertyValue = null;
        Iterator<PropertyValue> iterator = propertyValueList.iterator();
        while (iterator.hasNext()) {
            PropertyValue temp = iterator.next();
            if (temp.getName().equals(name)) {
                iterator.remove();
                propertyValue = temp;
            }
        }
        return propertyValue;
    }

    @Override
    public int propertyValueCount() {
        return propertyValueList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiPropertyValues)) return false;

        MultiPropertyValues that = (MultiPropertyValues) o;

        return propertyValueList != null ? propertyValueList.equals(that.propertyValueList) : that.propertyValueList == null;
    }

    @Override
    public int hashCode() {
        return propertyValueList != null ? propertyValueList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MultiPropertyValues{" +
                "propertyValueList=" + propertyValueList +
                '}';
    }
}
