package com.masonluo.fastframework.beans.factory.support;

import com.masonluo.fastframework.beans.annotation.ComponentScan;
import com.masonluo.fastframework.beans.annotation.Configuration;
import com.masonluo.fastframework.beans.factory.ConfigurableBeanFactory;
import com.masonluo.fastframework.beans.factory.config.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.stereotype.Component;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import com.masonluo.fastframework.beans.support.scanner.ClassPathBeanDefinitionScanner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author masonluo
 * @date 2020/7/2 2:17 PM
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        postProcessorComponent(registry);
    }

    private void postProcessorComponent(BeanDefinitionRegistry registry) {
        Map<String, BeanDefinition> beanDefinitionMap = registry.getBeanDefinitions();
        List<String> componentScanPackage = new ArrayList<>();
        for (BeanDefinition beanDefinition : beanDefinitionMap.values()) {
            if (beanDefinition instanceof AnnotationBeanDefinition) {
                String[] scanPackage = doPostProcessorComponent((AnnotationBeanDefinition) beanDefinition);
                if (scanPackage != null && scanPackage.length != 0) {
                    componentScanPackage.addAll(Arrays.asList(scanPackage));
                }
            }
        }
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        if (componentScanPackage.size() != 0) {
            componentScanPackage.forEach(scanner::scan);
        }
    }

    private String[] doPostProcessorComponent(AnnotationBeanDefinition beanDefinition) {
        if (beanDefinition.getAnnotationMetaData().hasAnnotation(Configuration.class)) {
            Annotation annotation = beanDefinition.getAnnotationMetaData().getAnnotation(ComponentScan.class);
            if (annotation != null) {
                return resolveComponentAnnotation(annotation);
            }
        }
        return null;
    }

    private String[] resolveComponentAnnotation(Annotation annotation) {
        ComponentScan componentScan = (ComponentScan) annotation;
        return componentScan.basePackages();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableBeanFactory beanFactory) {
        // empty method
    }
}
