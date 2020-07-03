package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.beans.annotation.Bean;
import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import com.masonluo.fastframework.beans.support.SingletonBeanRegistry;
import com.masonluo.fastframework.utils.Assert;
import com.masonluo.fastframework.utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author masonluo
 * @date 2020/6/28 4:11 PM
 */
public class DefaultAutowiredCapableBeanFactory extends AbstractAutowiredCapableBeanFactory
        implements ConfigurableAutowireCapableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private final Map<String, Set<String>> classToBeanName = new ConcurrentHashMap<>(256);

    @Override
    public void registryBeanDefinition(String beanName, BeanDefinition definition) {
        Assert.notNull(beanName);
        Assert.notNull(definition);
        beanDefinitionMap.put(beanName, definition);
        registerClassToBeanName(definition.getBeanClassName(), beanName);
    }

    @Override
    public void registerClassToBeanName(String className, String beanName) {
        Assert.notBlank(beanName);
        Assert.notBlank(className);
        synchronized (classToBeanName) {
            Set<String> beanNameSet = classToBeanName.get(className);
            if (beanNameSet == null) {
                beanNameSet = new TreeSet<>();
                classToBeanName.put(className, beanNameSet);
            }
            beanNameSet.add(beanName);
        }
    }

    @Override
    public BeanDefinition removeBeanDefinition(String beanName) {
        classToBeanName.remove(beanName);
        for (Set<String> set : classToBeanName.values()) {
            set.remove(beanName);
        }
        return beanDefinitionMap.remove(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @SuppressWarnings("unckeck")
    @Override
    public String getPrimaryBeanNameForClass(Class<?> clazz) {
        String beanName = clazz.getName();
        Set<String> beanNameSet = classToBeanName.get(beanName);
        if (beanNameSet == null || beanNameSet.isEmpty()) {
            return null;
        }
        TreeSet<String> set = (TreeSet<String>) beanNameSet;
        return set.first();
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitions() {
        return beanDefinitionMap;
    }

    @Override
    public void preInstantiateSingletons() {
        String[] beanNames = getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition definition = getBeanDefinition(beanName);
            if (definition.isSingleton() && !definition.isLazyInit()) {
                Object bean = null;
                if (isFactoryBean(beanName)) {
                    bean = getBean(FACTORY_BEAN_PREFIX + beanName);
                } else {
                    bean = getBean(beanName);
                }
                if (bean != null) {
                    resolveBeanAnnotated(bean, this);
                }
            }
        }
    }

    private void resolveBeanAnnotated(Object target, SingletonBeanRegistry registry) {
        Class<?> clazz = target.getClass();
        doResolveBeanAnnotation(target, clazz.getDeclaredMethods(), registry);
    }

    private void doResolveBeanAnnotation(Object target, Method[] declaredMethods, SingletonBeanRegistry registry) {
        if (declaredMethods == null || declaredMethods.length == 0) {
            return;
        }
        for (Method method : declaredMethods) {
            resolveBeanAnnotatedMethod(target, method, registry);
        }
    }

    private void resolveBeanAnnotatedMethod(Object target, Method method, SingletonBeanRegistry registry) {
        Assert.notNull(method);
        Assert.notNull(registry);
        if (method.isAnnotationPresent(Bean.class)) {
            String beanName = method.getName();
            Object bean = invokeMethod(target, method);
            Bean beanAnnotation = method.getAnnotation(Bean.class);
            if (!StringUtils.isBlank(beanAnnotation.value())) {
                beanName = beanAnnotation.value();
            }
            registry.registerSingleton(beanName, bean);
        }
    }

    private Object invokeMethod(Object target, Method method) {
        try {
            Class<?>[] classes = method.getParameterTypes();
            Object[] objects = new Object[classes.length];
            for (int i = 0; i < classes.length; i++) {
                Object bean = getBean(classes[i]);
                objects[i] = bean;
            }
            return method.invoke(target, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
