package com.masonluo.fastframework.beans.support;

import com.masonluo.fastframework.beans.factory.config.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.factory.config.StandardAnnotationBeanDefinition;
import com.masonluo.fastframework.core.annotation.AnnotationAttributes;
import com.masonluo.fastframework.core.meta.AnnotationMetaData;
import com.masonluo.fastframework.exception.RepeatedlyBeanNameException;
import com.masonluo.fastframework.utils.AnnotationConfigUtils;
import com.masonluo.fastframework.utils.ClassUtils;
import com.masonluo.fastframework.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

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
        // 优先根据注解里面的值生成BeanName
        String beanName = generateBeanNameFromAnnotation(beanDefinition.getAnnotationMetaData());
        if (StringUtils.isBlank(beanName)) {
            beanName = generateDefaultBeanName(definition);
        }
        if (registry.containsBeanDefinition(beanName)) {
            // BeanNameGenerator只应该生成BeanName，不应该去做异常抛出
            // 具体是否要抛出异常，留待外部判断
//            throw new BeanDefinitionExistException();
            return null;
        }

        return beanName;
    }

    private String generateDefaultBeanName(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        return StringUtils.firstToLowerCase(ClassUtils.getSimpleName(beanClassName));
    }

    /**
     * 根据注解的value生成beanName
     * TODO 只能根据特定注解生成，比如{@link com.masonluo.fastframework.beans.stereotype.Component}
     *
     * @param annotationMetaData
     * @return
     */
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
            public Map<String, BeanDefinition> getBeanDefinitions() {
                return null;
            }

            @Override
            public void registryBeanDefinition(String beanName, BeanDefinition definition) {

            }

            @Override
            public void registerClassToBeanName(String className, String beanName) {

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
            public String[] getBeanDefinitionNames() {
                return new String[0];
            }

            @Override
            public boolean containsBeanDefinition(String beanName) {
                return false;
            }

            @Override
            public String getPrimaryBeanNameForClass(Class<?> clazz) {
                return null;
            }
        });
        System.out.println(beanName);
    }
}
