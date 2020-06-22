package com.masonluo.fastframework.utils;

import com.masonluo.fastframework.beans.factory.config.beanDefinition.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.beanDefinition.StandardAnnotationBeanDefinition;
import com.masonluo.fastframework.core.annotation.Lazy;
import com.masonluo.fastframework.core.annotation.Primary;
import com.masonluo.fastframework.core.meta.AnnotationMetaData;

import java.lang.annotation.Annotation;

/**
 * @author masonluo
 * @date 2020/6/22 5:25 PM
 */
@Lazy(value = false)
@Primary(value = false)
public class AnnotationProcessUtils {
    /**
     * 处理通用的注解
     * {@link com.masonluo.fastframework.core.annotation.Lazy}
     * {@link com.masonluo.fastframework.core.annotation.Primary}
     *
     * @param beanDefinition
     */
    public static void processCommonAnnotation(AnnotationBeanDefinition beanDefinition) {
        processCommonAnnotation(beanDefinition, beanDefinition.getAnnotationMetaData());
    }

    /**
     * 目前仅仅处理Primary和Lazy注解
     *
     * @param beanDefinition
     * @param metaData
     */
    public static void processCommonAnnotation(AnnotationBeanDefinition beanDefinition, AnnotationMetaData metaData) {
        Lazy lazy = getAnnotation(metaData, Lazy.class);
        if (lazy != null) {
            beanDefinition.setLazyInit(lazy.value());
        }
        Primary primary = getAnnotation(metaData, Primary.class);
        if (lazy != null) {
            beanDefinition.setPrimary(true);
        }
    }

    public static <T> T getAnnotation(AnnotationMetaData metaData, Class<T> type) {
        Annotation annotation = metaData.getAnnotation(type);
        if (annotation != null) {
            return type.cast(annotation);
        }
        return null;
    }

    public static void main(String[] args) {
        AnnotationBeanDefinition annotationBeanDefinition = new StandardAnnotationBeanDefinition(AnnotationProcessUtils.class);
        processCommonAnnotation(annotationBeanDefinition);
        System.out.println();
    }
}
