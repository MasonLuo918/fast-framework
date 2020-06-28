package com.masonluo.fastframework.beans.factory;

import com.masonluo.fastframework.beans.BeanWrapper;
import com.masonluo.fastframework.beans.BeanWrapperImpl;
import com.masonluo.fastframework.beans.factory.config.GenericBeanDefinition;
import com.masonluo.fastframework.core.annotation.Autowired;
import com.masonluo.fastframework.exception.BeansException;
import com.masonluo.fastframework.utils.AnnotationUtils;
import com.masonluo.fastframework.utils.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return;
        }
        for (Field field : fields) {
            String name = field.getName();
            doAutowiredByName(bean, field, name);
        }
    }

    private void doAutowiredByName(Object target, Field field, String name) {
        Assert.notNull(field);
        Assert.notBlank(name);
        Assert.notNull(target);
        field.setAccessible(true);
        Object bean = getBean(name);
        if (bean == null) {
            throw new BeansException("Can't find a bean whose name is [" + name + "] to autowired into the field[" + field.getName() + "]");
        }
        try {
            field.set(target, bean);
        } catch (IllegalAccessException e) {
            throw new BeansException("Can't autowired into filed [" + field.getName() + "]");
        }
    }

    protected void autowiredByType(Object bean, String beanName, GenericBeanDefinition beanDefinition) {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0){
            return;
        }
        for (Field field : fields){
            doAutowiredByType(bean, field);
        }
    }

    private void doAutowiredByType(Object target, Field field){
        Assert.notNull(field);
        Assert.notNull(target);
        field.setAccessible(true);
        Object bean = getBean(field.getType());
        if (bean == null) {
            throw new BeansException("Can't find a bean whose class is [" + field.getType().getName() + "] to autowired into the field[" + field.getName() + "]");
        }
        try {
            field.set(target, bean);
        } catch (IllegalAccessException e) {
            throw new BeansException("Can't autowired into filed [" + field.getName() + "]");
        }
    }

    protected ObjectFactory<?> getEarlyReference(String beanName, Object bean) {
        return () -> bean;
    }

    protected BeanWrapper createBeanInstance(String beanName, GenericBeanDefinition beanDefinition) throws ClassNotFoundException {
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
        Assert.hasLength(constructors, "The class [" + beanName + "] does not has a constructor");
        Constructor defaultConstructor;
        List<Constructor> autowiredConstructor = obtainAutowiredConstructor(constructors);
        List<Constructor> normalConstructor = obtainNormalConstructor(constructors);
        defaultConstructor = obtainDefaultConstructor(constructors);
        Object bean = resolveAutowiredConstructors(beanName, beanDefinition, autowiredConstructor.toArray(new Constructor[0]));
        if (bean == null && defaultConstructor != null) {
            bean = resolveDefaultConstructor(defaultConstructor);
        }
        if (bean == null) {
            throw new BeansException("Can't find a suitable constructor to initial the bean ");
        }
        return bean;
    }

    protected Object resolveDefaultConstructor(Constructor defaultConstructor) {
        Object bean = null;
        try {
            bean = defaultConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }


    private Constructor obtainDefaultConstructor(Constructor[] constructors) {
        if (constructors == null || constructors.length == 0) {
            return null;
        }
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor;
            }
        }
        return null;
    }

    private Object resolveAutowiredConstructors(String beanName, GenericBeanDefinition beanDefinition, Constructor[] constructors) {
        Object bean;
        for (Constructor constructor : constructors) {
            bean = resolveAutowiredConstructor(constructor);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    private Object resolveAutowiredConstructor(Constructor constructor) {
        Parameter[] parameters = constructor.getParameters();
        Object[] objects = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> clazz = parameter.getType();
            Object param = getBean(clazz);
            if (param == null) {
                return null;
            }
            objects[i] = param;
        }
        Object bean = null;
        try {
            bean = constructor.newInstance(objects);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private List<Constructor> obtainNormalConstructor(Constructor[] constructors) {
        List<Constructor> result = Arrays.asList(constructors);
        result.removeAll(obtainAutowiredConstructor(constructors));
        result = result.stream()
                .filter(constructor -> constructor.getParameterCount() != 0)
                .collect(Collectors.toList());
        return result;
    }

    private List<Constructor> obtainAutowiredConstructor(Constructor[] constructors) {
        List<Constructor> result = new ArrayList<>();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() != 0 && isAllAutowiredSigned(constructor)) {
                result.add(constructor);
            }
        }
        return result;
    }

    private boolean isAllAutowiredSigned(Constructor constructor) {
        Assert.notNull(constructor);
        Annotation[][] annotations = constructor.getParameterAnnotations();
        for (Annotation[] annos : annotations) {
            if (!hasAutowiredSign(annos)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasAutowiredSign(Annotation[] annos) {
        if (annos == null || annos.length == 0) {
            return false;
        }
        for (Annotation anno : annos) {
            if (AnnotationUtils.isEqual(anno, Autowired.class)) {
                return true;
            }
        }
        return false;
    }
}
