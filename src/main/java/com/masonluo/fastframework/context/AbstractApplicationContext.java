package com.masonluo.fastframework.context;

import com.masonluo.fastframework.beans.factory.AutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.BeanFactory;
import com.masonluo.fastframework.beans.factory.ConfigurableAutowireCapableBeanFactory;
import com.masonluo.fastframework.exception.BeanNotFoundException;
import com.masonluo.fastframework.exception.BeansException;

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

    private Lock startupShutdownMonitor = new ReentrantLock();

    public AbstractApplicationContext() {

    }

    public AbstractApplicationContext(ApplicationContext parent) {
        this.parent = parent;
    }


    // TODO
    @Override
    public void refresh() {
        startupShutdownMonitor.lock();
        ConfigurableAutowireCapableBeanFactory beanFactory = obtainFreshBeanFactory();
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

    public abstract ConfigurableAutowireCapableBeanFactory getBeanFactory();

    /**
     * 刷新一个BeanFactory，主要用来装载配置文件中的BeanDefinition
     */
    protected abstract void refreshBeanFactory();
}
