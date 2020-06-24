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
     * �������浥��bean��map
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * ����������ǰ�ع�ĵ�����map
     */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    /**
     * ��������һ�δ���bean��ʱ���������bean��ǰ�ع⣬��ô��������һ��ObjectFactory���󣬽�����Ҫ��
     * bean������{@link ObjectFactory#getObject()}���أ�
     * ��{@link DefaultSingletonBeanRegistry#addSingletonFactory(String, ObjectFactory)}
     * ����ע��
     * <p>
     * ע�����֮�󣬵����bean���ڴ�����ʱ��ʹ��getBean���������ж�{@code earlySingletonObjects}������
     * �Ƿ��и�bean�����û�У�����singletonFactoriesѰ�ң����Ҵ�����Ȼ������objectFactory�Ƴ���������
     * ��bean�Ͷ�Ӧ��beanName���뵽earlySingletonObjects��
     * <p>
     * ����Ҫ����ȥearlySingletonObjects�Ƴ�bean����Ϊ����registerSingleton��ʱ�򣬻Ὣ���Ƴ�
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>();

    /**
     * ���Bean���ڴ����У���ô����ӵ�������
     */
    private final Set<String> singletonCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * ע������bean��������ǰ�ع�ģ�
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
        // ����Ҫ��singletonFactories��������ΪConcurrentHashMap���̰߳�ȫ��
        // ΪʲôҪ��singletonObjects���м����أ���Ϊ����Ҫ��ȡsingletonObject���Ƿ��������һ��bean����ӦbeanName
        // Ϊ��beanName�ģ����������ܻᵼ���̲߳���ȫ
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

    public Object getSingleton(String beanName, ObjectFactory<?> objectFactory) {
        Assert.notBlank(beanName, "bean name should not be blank");
        Assert.notNull(objectFactory, "object factory should not be null");
        synchronized (singletonObjects) {
            Object singletonObject = singletonObjects.get(beanName);
            if (singletonObject == null) {
                beforeSingletonCreation(beanName);
                // �����ʵ�ʵ�����createBean -> doCreateBean
                // ��doCreateBean�У��ֻὫbean��ǰ�ع�
                // Ҳ���ǵ���addSingletonFactory
                singletonObject = objectFactory.getObject();
                afterSingletonCreation(beanName);
                addSingleton(beanName, singletonObject);
            }
            return singletonObject;
        }
    }

    /**
     * �ж��Ƿ�һ���������ڴ�������
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
