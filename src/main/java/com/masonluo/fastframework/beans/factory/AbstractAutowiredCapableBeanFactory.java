package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.beans.BeanWrapper;
import com.masonluo.fastframework.beans.BeanWrapperImpl;
import com.masonluo.fastframework.beans.factory.config.GenericBeanDefinition;
import com.masonluo.fastframework.utils.Assert;
import com.masonluo.fastframework.utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author masonluo
 * @date 2020/6/27 1:54 PM
 */
public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory implements AutowiredCapableBeanFactory {


    @SuppressWarnings("unchecked")
    @Override
    public <T> T createBean(Class<T> beanType) {
        GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) getBeanDefinition(getPrimaryBeanNameForClass(beanType));
        return (T) createBean(beanType.getName(), genericBeanDefinition);
    }

    @Override
    protected Object createBean(String beanName, GenericBeanDefinition beanDefinition) {
        BeanWrapper instanceWrapper;
        try {
            instanceWrapper = createBeanInstance(beanName, beanDefinition);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("The bean [" + beanName + "] does not found a correct clazz");
        }

        Object bean = instanceWrapper.getWrapperInstance();

        boolean earlyExpose = (beanDefinition.isSingleton() && isSingletonCurrentInCreation(beanName));
        if (earlyExpose) {
            addSingletonFactory(beanName, getEarlyReference(beanName, bean));
        }
        populateBean(beanName, bean, beanDefinition);
        return bean;
    }

    protected void populateBean(String beanName, Object bean, GenericBeanDefinition beanDefinition) {
        int autowiredMode = beanDefinition.getAutowireMode();
        if (autowiredMode == AbstractAutowiredCapableBeanFactory.AUTOWIRE_NO) {
            return;
        }
        if (autowiredMode == AbstractAutowiredCapableBeanFactory.AUTOWIRE_BY_NAME) {
            autowiredByName(bean, beanName, beanDefinition);
        }
        if (autowiredMode == AbstractAutowiredCapableBeanFactory.AUTO_WIRE_BY_TYPE) {
            autowiredByType(bean, beanName, beanDefinition);
        }
    }

    protected void autowiredByName(Object bean, String beanName, GenericBeanDefinition beanDefinition) {
        // TODO
    }

    protected void autowiredByType(Object bean, String beanName, GenericBeanDefinition beanDefinition) {
        // TODO
    }

    protected ObjectFactory<?> getEarlyReference(String beanName, Object bean) {
        return new ObjectFactory<Object>() {
            @Override
            public Object getObject() throws RuntimeException {
                return bean;
            }
        };
    }

    protected BeanWrapper createBeanInstance(String beanName, GenericBeanDefinition beanDefinition) throws ClassNotFoundException {
        //TODO 进行bean的生成
        Object bean = null;
        Class<?> clazz = null;
        if (beanDefinition.hasBeanClass()) {
            clazz = beanDefinition.getBeanClass();
        } else {
            clazz = Class.forName(beanDefinition.getBeanClassName());
        }
        Constructor[] constructors = clazz.getConstructors();
        bean = useConstructorsToInstantiation(beanName, beanDefinition, constructors);
        if (bean == null) {
            throw new IllegalStateException();
        }
        return new BeanWrapperImpl(bean, clazz);
    }

    protected Object useConstructorsToInstantiation(String beanName, GenericBeanDefinition beanDefinition, Constructor[] constructors) {
        // TODO
        Assert.hasLength(constructors, "The class [" + beanName + "] does not has a constructor");
        Constructor defaultConstructor = null;
        List<Constructor> autowiredConstructor = new ArrayList<>();
        List<Constructor> normalConstructor = new ArrayList<>();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0 && defaultConstructor == null) {
                defaultConstructor = constructor;
            } else {
                Annotation[][] annotations = constructor.getParameterAnnotations();
                int length = annotations.length;
                for (Annotation[] annos : annotations){
                    for (Annotation anno : annos){
                        if ()
                    }
                }
            }
        }
        return null;
    }
}
