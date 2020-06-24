package com.masonluo.fastframework.beans.support;

import com.masonluo.fastframework.beans.factory.FactoryBean;
import com.masonluo.fastframework.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author masonluo
 * @date 2020/6/24 4:29 PM
 */
public class FactoryBeanSupportRegister extends DefaultSingletonBeanRegistry {
    /**
     * 用来缓存FactoryBean生成的object
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    public Class<?> getTypeForBeanFactory(FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    public Object getObjectFromBeanInstance(FactoryBean factoryBean, String beanName) {
        Assert.notNull(factoryBean);
        Assert.notNull(beanName);
        // 如果这个FactoryBean是单例的化
        if (factoryBean.isSingleton()) {
            synchronized (factoryBeanObjectCache) {
                Object bean = factoryBeanObjectCache.get(beanName);
                if (bean == null) {
                    bean = factoryBean.getObject();
                    factoryBeanObjectCache.put(beanName, bean);
                }
                return bean;
            }
        } else {
            return factoryBean.getObject();
        }
    }

    public FactoryBean<?> getfactoryBean(String beanName, Object beanInstance) {
        if (!(beanInstance instanceof FactoryBean)) {
            throw new IllegalStateException(beanName + " is not a factory bean");
        }
        return (FactoryBean<?>) beanInstance;
    }
}
