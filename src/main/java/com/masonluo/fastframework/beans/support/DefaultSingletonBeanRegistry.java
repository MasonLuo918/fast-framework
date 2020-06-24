package com.masonluo.fastframework.beans.support;

import com.masonluo.fastframework.beans.factory.ObjectFactory;
import com.masonluo.fastframework.utils.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author masonluo
 * @date 2020/6/23 2:41 PM
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 用来缓存单例bean的map
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 用来缓存提前曝光的单例的map
     */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    /**
     * 当单例第一次创建bean的时候，如果允许将bean提前曝光，那么将会生成一个ObjectFactory对象，将所需要的
     * bean对象用{@link ObjectFactory#getObject()}返回，
     * 用{@link DefaultSingletonBeanRegistry#addSingletonFactory(String, ObjectFactory)}
     * 进行注册
     * <p>
     * 注册完成之后，当这个bean还在创建的时候，使用getBean，会首先判断{@code earlySingletonObjects}这里面
     * 是否含有该bean，如果没有，就在singletonFactories寻找，并且创建，然后把这个objectFactory移除，把生成
     * 的bean和对应的beanName加入到earlySingletonObjects中
     * <p>
     * 不需要主动去earlySingletonObjects移除bean，因为调用registerSingleton的时候，会将其移除
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();

    /**
     * 如果Bean正在创建中，那么会添加到这里来
     */
    private final Set<String> singletonCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * 注册所有bean（包括提前曝光的）
     */
    private final Set<String> registeredSingletons = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final Object singletonMutex = new Object();

    public void beforeSingletonCreation(String beanName) {
        if (!singletonCurrentlyInCreation.add(beanName)) {
            throw new IllegalStateException("[" + beanName + "] is in creation");
        }
    }

    public void afterSingletonCreation(String beanName) {
        if (!singletonCurrentlyInCreation.remove(beanName)) {
            throw new IllegalStateException("[" + beanName + "] didn't in creation");
        }
    }

    public void addSingletonFactory(String beanName, ObjectFactory<?> objectFactory) {
        Assert.notBlank(beanName, "bean name should not be empty");
        Assert.notNull(objectFactory, "object factory should not be null");
        // 不需要对singletonFactories加锁，因为ConcurrentHashMap是线程安全的
        // 为什么要对singletonObjects进行加锁呢？因为我们要获取singletonObject中是否新添加了一个bean，对应beanName
        // 为该beanName的，不加锁可能会导致线程不安全
        synchronized (singletonObjects) {
            if (!singletonObjects.containsKey(beanName)) {
                earlySingletonObjects.remove(beanName);
                registeredSingletons.add(beanName);
                singletonFactories.put(beanName, objectFactory);
            }
        }
    }

    @Override
    public void registerSingleton(String beanName, Object singletonBean) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull("Singleton Object must not be null");
        // 需要加锁，不加锁可能会导致线程不安全
        synchronized (singletonObjects) {
            Object bean = singletonObjects.get(beanName);
            if (bean != null) {
                throw new IllegalStateException("The bean name [" + beanName + "] has exist in the container");
            }
            addSingleton(beanName, singletonBean);
        }
    }

    protected void addSingleton(String beanName, Object singletonBean) {
        synchronized (singletonObjects) {
            singletonObjects.put(beanName, singletonBean);
            earlySingletonObjects.remove(beanName);
            singletonFactories.remove(beanName);
            registeredSingletons.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    /**
     * bean构造策略
     * <p>
     * 如果允许循环依赖，那么我们就需要提前曝光我们的bean
     * <p>
     * 我们会将bean包装成ObjectFactory进行曝光
     * <p>
     * 第一个获取bean的时候，会将ObjectFactory里面的bean提取出来，并且加入earlySingletonObjects中
     *
     * @param beanName
     * @param allowEarlyReference
     * @return
     */
    private Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object bean = singletonObjects.get(beanName);
        if (bean == null && isSingletonCurrentInCreation(beanName)) {
            synchronized (singletonObjects) {
                bean = earlySingletonObjects.get(beanName);
                if (bean == null && allowEarlyReference) {
                    ObjectFactory objectFactory = singletonFactories.get(beanName);
                    if (objectFactory != null) {
                        bean = objectFactory.getObject();
                        earlySingletonObjects.put(beanName, bean);
                        singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return bean;
    }

    public Object getSingleton(String beanName, ObjectFactory<?> objectFactory) {
        Assert.notBlank(beanName, "bean name should not be blank");
        Assert.notNull(objectFactory, "object factory should not be null");
        synchronized (singletonObjects) {
            Object singletonObject = singletonObjects.get(beanName);
            if (singletonObject == null) {
                beforeSingletonCreation(beanName);
                // 在这里，实际调用了createBean -> doCreateBean
                // 在doCreateBean中，又会将bean提前曝光
                // 也就是调用addSingletonFactory
                singletonObject = objectFactory.getObject();
                afterSingletonCreation(beanName);
                addSingleton(beanName, singletonObject);
            }
            return singletonObject;
        }
    }

    /**
     * 判断是否一个单例正在创建当中
     */
    public boolean isSingletonCurrentInCreation(String beanName) {
        synchronized (singletonCurrentlyInCreation) {
            return singletonCurrentlyInCreation.contains(beanName);
        }
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return registeredSingletons.contains(beanName);
    }

    @Override
    public int getSingletonCount() {
        return registeredSingletons.size();
    }

    @Override
    public String[] getSingletonNames() {
        return registeredSingletons.toArray(new String[getSingletonCount()]);
    }

    public Object getSingletonMutex() {
        return singletonMutex;
    }
}
