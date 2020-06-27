package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.beans.factory.config.AbstractBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.factory.config.GenericBeanDefinition;
import com.masonluo.fastframework.beans.support.FactoryBeanSupportRegister;
import com.masonluo.fastframework.exception.BeanCurrentlyInCreationException;
import com.masonluo.fastframework.exception.BeanNotFoundException;
import com.masonluo.fastframework.exception.BeansException;
import com.masonluo.fastframework.utils.Assert;
import com.masonluo.fastframework.utils.BeanFactoryUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author masonluo
 * @date 2020/6/24 5:10 PM
 */
public abstract class AbstractBeanFactory extends FactoryBeanSupportRegister implements ConfigurableBeanFactory {

    private BeanFactory parent;

    /**
     * 为什么这里用ThreadLocal呢？ 因为如果是原型的循环依赖的话，只会在同一个线程创建bean
     * 所以判断循环以来的依据就是：在同一个线程中，反复创建两次bean
     */
    private ThreadLocal<Object> prototypeInCreation = new ThreadLocal<>();

    @Override
    public void setParentBeanFactory(BeanFactory parent) {
        this.parent = parent;
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return parent;
    }

    @Override
    public boolean containsInLocal(String name) {
        return containsSingleton(name) || containsBeanDefinition(name);
    }

    @Override
    public Object getBean(String name) throws BeanNotFoundException {
        return getBean(name, null);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return doGetBean(name, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        String beanName = getPrimaryBeanNameForClass(requiredType);
        if (beanName == null) {
            throw new BeanNotFoundException("The class name for [" + requiredType.getName() + "] does not register");
        }
        return getBean(beanName, requiredType);
    }

    @SuppressWarnings("unchecked")
    public <T> T doGetBean(String name, final Class<T> requiredType) {
        final String beanName = transformBeanName(name);
        Object bean, shareSingleton;
        shareSingleton = getSingleton(beanName);
        if (shareSingleton != null) {
            bean = getObjectForBeanInstance(shareSingleton, name, beanName);
        } else {
            if (isPrototypeInCreation(beanName)) {
                throw new BeanCurrentlyInCreationException(beanName);
            }

            BeanFactory parent = getParentBeanFactory();
            if (parent != null && !containsBeanDefinition(beanName)) {
                if (requiredType != null) {
                    return parent.getBean(name, requiredType);
                } else {
                    return (T) parent.getBean(name);
                }
            }

            BeanDefinition definition = getBeanDefinition(beanName);
            if (definition == null) {
                throw new IllegalArgumentException("[ " + beanName + " ] does not register");
            }
            if (!(definition instanceof AbstractBeanDefinition)) {
                throw new IllegalArgumentException("this bean definition does not support");
            }
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) definition;
            if (beanDefinition.isSingleton()) {
                shareSingleton = getSingleton(beanName, () -> {
                    return createBean(beanName, beanDefinition);
                });
                bean = getObjectForBeanInstance(shareSingleton, name, beanName);
            } else if (beanDefinition.isPrototype()) {
                beforePrototypeCreated(beanName);
                shareSingleton = createBean(beanName, beanDefinition);
                bean = getObjectForBeanInstance(shareSingleton, name, beanName);
                afterPrototypeCreated(beanName);
            } else {
                throw new IllegalStateException("Only prototype and singleton models are temporarily support");
            }
        }
        return (T) bean;
    }

    @SuppressWarnings("unchecked")
    private void beforePrototypeCreated(String prototypeName) {
        Object val = prototypeInCreation.get();
        if (val == null) {
            prototypeInCreation.set(prototypeName);
        } else if (val instanceof String) {
            String strVal = (String) val;
            Set<String> set = new HashSet<>();
            set.add(strVal);
            set.add(prototypeName);
            prototypeInCreation.set(set);
        } else {
            Set<String> set = (Set<String>) val;
            set.add(prototypeName);
        }
    }

    @SuppressWarnings("unchecked")
    private void afterPrototypeCreated(String prototypeName) {
        Object val = prototypeInCreation.get();
        if (val != null) {
            if (val instanceof String) {
                prototypeInCreation.remove();
            } else {
                Set<String> set = (Set<String>) val;
                set.remove(prototypeName);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private boolean isPrototypeInCreation(String beanName) {
        Assert.notBlank(beanName);
        Object val = prototypeInCreation.get();
        if (val == null) {
            return false;
        } else if (val instanceof String) {
            return beanName.equals(val);
        } else {
            Set<String> set = (Set<String>) val;
            return set.contains(beanName);
        }
    }

    /**
     * 对传入的name进行处理
     * 1. 可能传入的是工厂前缀， 例如&+beanName, 这个时候返回beanName
     * 2. 可能传入的是别名(暂时不进行判断)
     *
     * @param name
     * @return
     */
    private String transformBeanName(String name) {
        Assert.notBlank(name);
        if (name.startsWith("&")) {
            if (name.length() == 1) {
                throw new IllegalArgumentException("[ " + name + "] - bean name should not be blank");
            }
            return name.substring(1, name.length());
        }
        return name;
    }

    protected Object getObjectForBeanInstance(Object shareSingleton, String name, String beanName) {
        // 如果要求的是FactoryBEan
        if (BeanFactoryUtils.isFactoryBeanReference(name)) {
            if (!(shareSingleton instanceof FactoryBean)) {
                throw new IllegalStateException("Bean [" + shareSingleton + "] is not a factory bean");
            }
            return shareSingleton;
        }

        // 如果要求的是原来的Bean
        if (!(shareSingleton instanceof FactoryBean) || BeanFactoryUtils.isFactoryBeanReference(name)) {
            return shareSingleton;
        }


        Object bean = getCachedObjectForFactoryBean(beanName);

        if (bean == null) {
            // BeanFactory
            FactoryBean<?> factoryBean = (FactoryBean<?>) shareSingleton;
            bean = getObjectFromBeanInstance(factoryBean, beanName);
        }

        return bean;
    }

    @Override
    public boolean isSingleton(String name) {
        String beanName = transformBeanName(name);

        BeanFactory parent = getParentBeanFactory();
        if (parent != null && !containsBeanDefinition(beanName)) {
            return parent.isSingleton(name);
        }
        BeanDefinition definition = getBeanDefinition(beanName);
        return definition != null && definition.isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        String beanName = transformBeanName(name);

        BeanFactory parent = getParentBeanFactory();
        if (parent != null && !containsBeanDefinition(beanName)) {
            return parent.isPrototype(name);
        }
        BeanDefinition definition = getBeanDefinition(beanName);
        return definition != null && definition.isPrototype();
    }

    protected abstract boolean containsBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, GenericBeanDefinition beanDefinition);

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract String getPrimaryBeanNameForClass(Class<?> clazz);
}
