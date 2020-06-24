package com.masonluo.fastframework.core.annotation;

import com.masonluo.fastframework.core.meta.StandardAnnotationMetaData;
import com.masonluo.fastframework.utils.AnnotationConfigUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author masonluo
 * @date 2020/6/22 7:34 PM
 */
public class AnnotationAttributes extends LinkedHashMap<String, Object> {

    public AnnotationAttributes(int initialCapacity) {
        super(initialCapacity);
    }

    public AnnotationAttributes() {

    }

    public AnnotationAttributes(Map<? extends String, ?> m) {
        super(m);
    }

    public static AnnotationAttributes fromMap(Map<String, Object> map) {
        return new AnnotationAttributes(map);
    }

    public static void main(String[] args) {
        AnnotationAttributes annotationAttributes = fromMap(
                AnnotationConfigUtils.toMap(new StandardAnnotationMetaData(AnnotationConfigUtils.class), Lazy.class)
        );
        System.out.println(annotationAttributes.size());

    }
}
