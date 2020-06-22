package com.masonluo.fastframework.utils;

import com.masonluo.fastframework.beans.factory.config.beanDefinition.AnnotationBeanDefinition;
import com.masonluo.fastframework.core.annotation.Lazy;
import com.masonluo.fastframework.core.annotation.Primary;
import com.masonluo.fastframework.core.meta.AnnotationMetaData;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author masonluo
 * @date 2020/6/22 5:25 PM
 */
@Lazy(value = false)
@Primary(value = false)
public class AnnotationConfigUtils {
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
        if (primary != null) {
            beanDefinition.setPrimary(primary.value());
        }
    }

    /**
     * 根据类型，获取AnnotationMetaData中的注解
     * <p>
     * 做类型转换
     *
     * @param metaData
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getAnnotation(AnnotationMetaData metaData, Class<T> type) {
        Annotation annotation = metaData.getAnnotation(type);
        if (annotation != null) {
            return type.cast(annotation);
        }
        return null;
    }

    public static Map<String, Object> toMap(AnnotationMetaData metaData, Class<?> clazz) {
        Map<String, Object> attrMap = new HashMap<>();
        Annotation[] annotations = metaData.getAnnotations();
        if (annotations == null || annotations.length == 0) {
            return attrMap;
        }
        Annotation annotation = Arrays.stream(annotations)
                .filter(a -> a.annotationType().equals(clazz))
                .findFirst().get();
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        Stream.of(methods).forEach(action -> {
            try {
                attrMap.put(action.getName(), action.invoke(annotation, (Object[]) null));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException();
            }
        });
        return attrMap;
    }

}
