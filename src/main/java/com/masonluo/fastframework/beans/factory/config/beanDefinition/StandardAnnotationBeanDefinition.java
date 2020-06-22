package com.masonluo.fastframework.beans.factory.config.beanDefinition;

import com.masonluo.fastframework.core.meta.AnnotationMetaData;
import com.masonluo.fastframework.core.meta.StandardAnnotationMetaData;

/**
 * @author masonluo
 * @date 2020/6/22 2:26 PM
 */
public class StandardAnnotationBeanDefinition extends AbstractBeanDefinition implements AnnotationBeanDefinition {
    private AnnotationMetaData annotationMetaData;

    public StandardAnnotationBeanDefinition(Class<?> beanClass) {
        annotationMetaData = new StandardAnnotationMetaData(beanClass);
        setBeanClass(beanClass);
        setBeanClassName(beanClass.getName());
    }

    @Override
    public AnnotationMetaData getAnnotationMetaData() {
        return annotationMetaData;
    }
}
