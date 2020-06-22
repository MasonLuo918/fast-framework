package com.masonluo.fastframework.beans.support;

import com.masonluo.fastframework.beans.factory.config.beanDefinition.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.beanDefinition.BeanDefinition;
import com.masonluo.fastframework.core.meta.AnnotationMetaData;

/**
 * @author masonluo
 * @date 2020/6/22 6:02 PM
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        AnnotationBeanDefinition beanDefinition = null;
        if (definition instanceof AnnotationBeanDefinition) {
            beanDefinition = (AnnotationBeanDefinition) definition;
        } else {
            return null;
        }
        String beanName = generateBeanNameFromAnnotation(beanDefinition.getAnnotationMetaData());
        return null;
    }

    private String generateBeanNameFromAnnotation(AnnotationMetaData annotationMetaData) {
        // TODO BeanComponentRegistry 注册一些注解，被这些注解标记的会被加载成为Bean
        return null;
    }
}
