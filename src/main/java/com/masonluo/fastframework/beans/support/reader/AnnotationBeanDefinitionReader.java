package com.masonluo.fastframework.beans.support.reader;

import com.masonluo.fastframework.beans.factory.config.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.StandardAnnotationBeanDefinition;
import com.masonluo.fastframework.beans.support.AnnotationBeanNameGenerator;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import com.masonluo.fastframework.beans.support.BeanNameGenerator;
import com.masonluo.fastframework.utils.AnnotationConfigUtils;

/**
 * @author masonluo
 * @date 2020/6/22 3:55 PM
 */
public class AnnotationBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    /**
     * 生成BeanName
     */
    private BeanNameGenerator generator = new AnnotationBeanNameGenerator();

    public AnnotationBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public int loadBeanDefinition(String className) throws ClassNotFoundException {
        int count = getRegistry().getBeanDefinitionCount();
        Class<?> clazz = Class.forName(className);
        register(clazz);
        return getRegistry().getBeanDefinitionCount() - count;
    }

    public void register(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            registerBean(clazz);
        }
    }

    private void registerBean(Class<?> clazz) {
        AnnotationBeanDefinition beanDefinition = new StandardAnnotationBeanDefinition(clazz);
        // 处理通用的注解
        AnnotationConfigUtils.processCommonAnnotation(beanDefinition);
        String beanName = generator.generateBeanName(beanDefinition, registry);
        if (beanName == null){
            throw new IllegalStateException("bean name generate error!");
        }
        registry.registryBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public int loadBeanDefinition(String... className) throws ClassNotFoundException {
        int count = 0;
        for (String clazz : className) {
            count += loadBeanDefinition(clazz);
        }
        return count;
    }

}
