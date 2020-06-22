package com.masonluo.fastframework.core.meta;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author masonluo
 * @date 2020/6/22 2:10 PM
 */
public interface AnnotationMetaData {

    Annotation[] getAnnotations();

    Set<String> getAnnotationType();

    default int getAnnotationCount() {
        return getAnnotations().length;
    }

    default boolean hasAnnotation(Class<?> clazz) {
        return getAnnotation(clazz) != null;
    }

    default Annotation getAnnotation(Class<?> clazz) {
        if (getAnnotations() == null) {
            return null;
        }
        for (Annotation annotation : getAnnotations()) {
            if (annotation.annotationType().equals(clazz)) {
                return annotation;
            }
        }
        return null;
    }
}
