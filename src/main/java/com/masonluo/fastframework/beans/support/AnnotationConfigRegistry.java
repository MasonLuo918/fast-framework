package com.masonluo.fastframework.beans.support;

/**
 * @author masonluo
 * @date 2020/7/2 4:44 PM
 */
public interface AnnotationConfigRegistry {
    void register(Class<?>... classes);

    void scan(String... basePackages);
}
