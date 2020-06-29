package com.masonluo.fastframework.test;

import com.masonluo.fastframework.beans.factory.AbstractAutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.DefaultAutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.config.GenericBeanDefinition;

/**
 * // TODO 查看循环依赖解决处
 * // TODO 1. 添加有参数的getBean
 * // TODO 2. 解决构造参数注入和setter注入冲突的情况
 * @author masonluo
 * @date 2020/6/27 9:49 PM
 */
public class Main {
    public static void main(String[] args) {
        GenericBeanDefinition a = new GenericBeanDefinition();
        a.setBeanClass(A.class);
        a.setAutowireMode(AbstractAutowiredCapableBeanFactory.AUTOWIRE_BY_TYPE);
        GenericBeanDefinition b = new GenericBeanDefinition();
        b.setBeanClass(B.class);
        b.setAutowireMode(AbstractAutowiredCapableBeanFactory.AUTOWIRE_BY_TYPE);
        GenericBeanDefinition c = new GenericBeanDefinition();
        c.setBeanClass(C.class);
        c.setAutowireMode(AbstractAutowiredCapableBeanFactory.AUTOWIRE_BY_TYPE);
        GenericBeanDefinition d = new GenericBeanDefinition();
        d.setBeanClass(D.class);
        d.setAutowireMode(AbstractAutowiredCapableBeanFactory.AUTOWIRE_BY_TYPE);
        DefaultAutowiredCapableBeanFactory beanFactory = new DefaultAutowiredCapableBeanFactory();
        beanFactory.registryBeanDefinition("a", a);
        beanFactory.registryBeanDefinition("b", b);
        beanFactory.registryBeanDefinition("c", c);
        beanFactory.registryBeanDefinition("d", d);
        B clazzC = beanFactory.getBean(B.class);
        System.out.println(clazzC);
    }
}
