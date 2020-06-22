package com.masonluo.fastframework.beans.support;

import com.masonluo.fastframework.beans.factory.config.beanDefinition.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.beanDefinition.BeanDefinition;
import com.masonluo.fastframework.beans.factory.config.beanDefinition.StandardAnnotationBeanDefinition;
import com.masonluo.fastframework.beans.stereotype.Controller;
import com.masonluo.fastframework.core.annotation.AnnotationAttributes;
import com.masonluo.fastframework.core.meta.AnnotationMetaData;
import com.masonluo.fastframework.exception.BeanDefinitionExistException;
import com.masonluo.fastframework.exception.RepeatedlyBeanNameException;
import com.masonluo.fastframework.utils.AnnotationConfigUtils;
import com.masonluo.fastframework.utils.ClassUtils;
import com.masonluo.fastframework.utils.StringUtils;

import java.lang.annotation.Annotation;

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
        if (StringUtils.isBlank(beanName)) {
            beanName = generateDefaultBeanName(definition);
        }
        if (registry.containsBeanDefinition(beanName)) {
            throw new BeanDefinitionExistException();
        }

        return beanName;
    }

    private String generateDefaultBeanName(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        return StringUtils.firstToLowerCase(ClassUtils.getSimpleName(beanClassName));
    }

    private String generateBeanNameFromAnnotation(AnnotationMetaData annotationMetaData) {
        String beanName = "";
        String strVal;
        for (Annotation annotation : annotationMetaData.getAnnotations()) {
            AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                    AnnotationConfigUtils.toMap(annotationMetaData, annotation.annotationType()));
            Object val = attributes.get("value");
            if (val instanceof String) {
                strVal = (String) val;
                if (StringUtils.hasText(strVal)) {
                    if (StringUtils.isEqual(strVal, beanName)) {
                        throw new RepeatedlyBeanNameException();
                    }
                    beanName = strVal;
                }
            }
        }
        return beanName;
    }

    public static void main(String[] args) {
        AnnotationBeanDefinition beanDefinition = new StandardAnnotationBeanDefinition(AnnotationBeanNameGenerator.class);
        AnnotationBeanNameGenerator generator = new AnnotationBeanNameGenerator();
        String beanName = generator.generateBeanName(beanDefinition, new BeanDefinitionRegistry() {
            @Override
            public void registryBeanDefinition(String beanName, BeanDefinition definition) {

            }

            @Override
            public BeanDefinition removeBeanDefinition(String beanName) {
                return null;
            }

            @Override
            public int getBeanDefinitionCount() {
                return 0;
            }

            @Override
            public String[] getBeanNames() {
                return new String[0];
            }

            @Override
            public boolean containsBeanDefinition(String beanName) {
                return false;
            }
        });
        System.out.println(beanName);
    }
}
