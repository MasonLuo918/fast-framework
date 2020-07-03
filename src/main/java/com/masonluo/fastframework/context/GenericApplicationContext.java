package com.masonluo.fastframework.context;

import com.masonluo.fastframework.beans.factory.ConfigurableAutowireCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.DefaultAutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;

import java.util.Map;

/**
 * @author masonluo
 * @date 2020/7/2 4:36 PM
 */
public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private DefaultAutowiredCapableBeanFactory beanFactory;

    public GenericApplicationContext(){
        beanFactory = new DefaultAutowiredCapableBeanFactory();
    }

    public GenericApplicationContext(DefaultAutowiredCapableBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    @Override
    public ConfigurableAutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    protected void refreshBeanFactory() {

    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitions() {
        return beanFactory.getBeanDefinitions();
    }

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition definition) {
        beanFactory.registryBeanDefinition(beanName, definition);
    }

    @Override
    public void registerClassToBeanName(String className, String beanName) {
        beanFactory.registerClassToBeanName(className, beanName);
    }

    @Override
    public BeanDefinition removeBeanDefinition(String beanName) {
        return beanFactory.removeBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return beanFactory.getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanFactory.getBeanDefinitionNames();
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanFactory.containsBeanDefinition(beanName);
    }

    @Override
    public String getPrimaryBeanNameForClass(Class<?> clazz) {
        return beanFactory.getPrimaryBeanNameForClass(clazz);
    }
}
