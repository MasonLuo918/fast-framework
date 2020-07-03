package com.masonluo.fastframework.context;

import com.masonluo.fastframework.beans.factory.AutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.BeanFactory;
import com.masonluo.fastframework.beans.factory.ConfigurableAutowireCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.ConfigurableBeanFactory;
import com.masonluo.fastframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.masonluo.fastframework.beans.factory.support.BeanFactoryPostProcessor;
import com.masonluo.fastframework.beans.factory.support.ConfigurationClassPostProcessor;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import com.masonluo.fastframework.exception.BeanNotFoundException;
import com.masonluo.fastframework.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author masonluo
 * @date 2020/7/2 10:41 AM
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    private String id;

    private String applicationContextName;

    private ApplicationContext parent;

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    private Lock startupShutdownMonitor = new ReentrantLock();

    public AbstractApplicationContext() {
        this(null);
    }

    public AbstractApplicationContext(ApplicationContext parent) {
        registerDefaultBeanFactoryPostProcessor();
        this.parent = parent;
    }


    @Override
    public void refresh() {
        startupShutdownMonitor.lock();
        ConfigurableAutowireCapableBeanFactory beanFactory = obtainFreshBeanFactory();
        prepareBeanFactory(beanFactory);
        invokeBeanFactoryPostProcessor(beanFactory);
        finishBeanFactoryInitialization(beanFactory);
    }

    private void finishBeanFactoryInitialization(ConfigurableAutowireCapableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    protected void invokeBeanFactoryPostProcessor(ConfigurableBeanFactory beanFactory) {
        if (beanFactoryPostProcessors.size() != 0) {
            beanFactoryPostProcessors.forEach(beanFactoryPostProcessor -> {
                beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
                if (beanFactoryPostProcessor instanceof BeanDefinitionRegistryPostProcessor
                        && beanFactory instanceof BeanDefinitionRegistry) {
                    BeanDefinitionRegistryPostProcessor registryPostProcessor = (BeanDefinitionRegistryPostProcessor) beanFactoryPostProcessor;
                    registryPostProcessor.postProcessBeanDefinitionRegistry((BeanDefinitionRegistry) beanFactory);
                }
            });
        }
    }

    protected void prepareBeanFactory(ConfigurableAutowireCapableBeanFactory beanFactory) {
    }

    /**
     * 获得一个ConfigurableAutowireCapableBeanFactory实例
     */
    protected ConfigurableAutowireCapableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setApplicationContextName(String applicationContextName) {
        this.applicationContextName = applicationContextName;
    }

    @Override
    public void setParentApplicationContext(ApplicationContext context) {
        this.parent = context;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getApplicationContextName() {
        return applicationContextName;
    }

    @Override
    public ApplicationContext getParentApplicationContext() {
        return parent;
    }

    @Override
    public AutowiredCapableBeanFactory getAutowiredCapableBeanFactory() {
        return getBeanFactory();
    }

    @Override
    public void setParentBeanFactory(BeanFactory parent) {
        getBeanFactory().setParentBeanFactory(parent);
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return this.parent;
    }

    @Override
    public boolean containsInLocal(String name) {
        return getBeanFactory().containsInLocal(name);
    }

    @Override
    public Object getBean(String beanName) throws BeanNotFoundException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return getBeanFactory().isSingleton(beanName);
    }

    @Override
    public boolean isPrototype(String beanName) {
        return getBeanFactory().isPrototype(beanName);
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor processor) {
        beanFactoryPostProcessors.add(processor);
    }


    protected void registerDefaultBeanFactoryPostProcessor() {
        beanFactoryPostProcessors.add(new ConfigurationClassPostProcessor());
    }


    @Override
    public void preInstantiateSingletons() {
        getBeanFactory().preInstantiateSingletons();
    }

    @Override
    public boolean isFactoryBean(String beanName) {
        return getBeanFactory().isFactoryBean(beanName);
    }


    public abstract ConfigurableAutowireCapableBeanFactory getBeanFactory();

    /**
     * 刷新一个BeanFactory，主要用来装载配置文件中的BeanDefinition
     */
    protected abstract void refreshBeanFactory();
}
