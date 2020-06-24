package com.masonluo.fastframework.core.meta;

import com.masonluo.fastframework.utils.AnnotationConfigUtils;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author masonluo
 * @date 2020/6/22 2:15 PM
 */
public class StandardAnnotationMetaData implements AnnotationMetaData {

    private final Class<?> originClass;

    private Annotation[] annotations;


    public StandardAnnotationMetaData(Class<?> originClass) {
        this.originClass = originClass;
        annotations = originClass.getAnnotations();
    }

    @Override
    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public Set<String> getAnnotationType() {
        return Stream.of(annotations).map(annotation -> annotation.annotationType().getName())
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        StandardAnnotationMetaData metaData = new StandardAnnotationMetaData(AnnotationConfigUtils.class);
        for (String str : metaData.getAnnotationType()){
            System.out.println(str);
        }
    }

    public Class<?> getOriginClass() {
        return originClass;
    }
}
