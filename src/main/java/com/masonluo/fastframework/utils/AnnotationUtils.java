package com.masonluo.fastframework.utils;

import java.lang.annotation.Annotation;

/**
 * @author masonluo
 * @date 2020/6/28 1:38 PM
 */
public class AnnotationUtils {
    public static boolean isEqual(Annotation annotation, Class<?> clazz){
        if (annotation == null && clazz == null){
            return true;
        }
        if (annotation == null || clazz == null){
            return false;
        }
        return annotation.annotationType().equals(clazz);
    }

}
