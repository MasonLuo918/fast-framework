package com.masonluo.fastframework.beans.support.reader;

import com.masonluo.fastframework.beans.factory.config.beanDefinition.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.beanDefinition.StandardAnnotationBeanDefinition;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import com.masonluo.fastframework.utils.AnnotationProcessUtils;
import com.sun.deploy.util.StringUtils;

/**
 * @author masonluo
 * @date 2020/6/22 3:55 PM
 */
public class AnnotationBeanDefinitionReader implements BeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    public AnnotationBeanDefinitionReader(BeanDefinitionRegistry registry){
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
        for (Class<?> clazz : classes){
            registerBean(clazz);
        }
    }

    private void registerBean(Class<?> clazz) {
        AnnotationBeanDefinition beanDefinition = new StandardAnnotationBeanDefinition(clazz);
        // 处理通用的注解
        AnnotationProcessUtils.processCommonAnnotation(beanDefinition);

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
