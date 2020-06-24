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

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();

    private final Set<String> singletonCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final Set<String> registeredSingletons = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final Object singletonMutex = new Object();

    @Override
    public void registerSingleton(String beanName, Object singletonBean) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull("Singleton Object must not be null");
        // ��Ҫ���������������ܻᵼ���̲߳���ȫ
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
     * bean�������
     * <p>
     * �������ѭ����������ô���Ǿ���Ҫ��ǰ�ع����ǵ�bean
     * <p>
     * ���ǻὫbean��װ��ObjectFactory�����ع�
     * <p>
     * ��һ����ȡbean��ʱ�򣬻ὫObjectFactory�����bean��ȡ���������Ҽ���earlySingletonObjects��
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

    /**
     * �ж��Ƿ�һ���������ڴ�������
     */
    private boolean isSingletonCurrentInCreation(String beanName) {
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

    public Object getSingletonMutex(){
        return singletonMutex;
    }
}
