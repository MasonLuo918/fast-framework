package com.masonluo.fastframework.test;

import com.masonluo.fastframework.beans.factory.DefaultAutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.config.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.StandardAnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.support.ConfigurationClassPostProcessor;

/**
 * TODO 1. 添加有参数的getBean
 *
 * @author masonluo
 * @date 2020/6/27 9:49 PM
 */
public class Main {
    public static void main(String[] args) {
        AnnotationBeanDefinition a = new StandardAnnotationBeanDefinition(A.class);
        AnnotationBeanDefinition b = new StandardAnnotationBeanDefinition(B.class);
        AnnotationBeanDefinition c = new StandardAnnotationBeanDefinition(C.class);
        AnnotationBeanDefinition d = new StandardAnnotationBeanDefinition(D.class);

        DefaultAutowiredCapableBeanFactory beanFactory = new DefaultAutowiredCapableBeanFactory();
        beanFactory.registryBeanDefinition("a", a);
        beanFactory.registryBeanDefinition("b", b);
        beanFactory.registryBeanDefinition("c", c);
        beanFactory.registryBeanDefinition("d", d);
        ConfigurationClassPostProcessor processor = new ConfigurationClassPostProcessor();
        processor.postProcessBeanDefinitionRegistry(beanFactory);
        System.out.println();
    }
}
