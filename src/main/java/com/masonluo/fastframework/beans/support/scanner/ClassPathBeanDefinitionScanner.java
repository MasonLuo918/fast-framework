package com.masonluo.fastframework.beans.support.scanner;

import com.masonluo.fastframework.beans.annotation.Configuration;
import com.masonluo.fastframework.beans.factory.DefaultAutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.factory.config.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.StandardAnnotationBeanDefinition;
import com.masonluo.fastframework.beans.stereotype.Component;
import com.masonluo.fastframework.beans.stereotype.Controller;
import com.masonluo.fastframework.beans.stereotype.Service;
import com.masonluo.fastframework.beans.support.AnnotationBeanNameGenerator;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import com.masonluo.fastframework.beans.support.BeanNameGenerator;
import com.masonluo.fastframework.utils.AnnotationConfigUtils;
import com.masonluo.fastframework.utils.StringUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author masonluo
 * @date 2020/6/30 10:55 PM
 */
public class ClassPathBeanDefinitionScanner implements ConfigurableScanner {

    private BeanDefinitionRegistry registry;

    private BeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();

    private Set<Class<? extends Annotation>> scanAnnotation = new HashSet<>();

    private Set<String> scanClassName = new HashSet<>();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this(registry, true);
    }

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean registerDefaultAnnotation) {
        this.registry = registry;
        if (registerDefaultAnnotation) {
            registerDefaultAnnotation();
        }
    }

    @Override
    public <T extends Annotation> void addScanAnnotation(Class<T> annotation) {
        scanAnnotation.add(annotation);
    }

    @Override
    public <T extends Annotation> void removeScanAnnotation(Class<T> annotation) {
        scanAnnotation.remove(annotation);
    }

    @SuppressWarnings("uncheck")
    @Override
    public void scan(final String basePackage) {
        String basePath = StringUtils.packageToPath(basePackage);
        URL url = getClass().getClassLoader().getResource(basePath);
        if (url == null) {
            throw new IllegalStateException("The package [" + basePackage + "] does not exist");
        }
        File file = new File(url.getFile());
        File[] listFile = file.listFiles();
        if (listFile == null || listFile.length == 0) {
            return;
        }
        Stream.of(listFile).forEach(f -> {
            if (f.isDirectory()) {
                scan(StringUtils.append(basePackage + "." + f.getName()));
            } else {
                if (f.getName().endsWith(".class")) {
                    doScan(StringUtils.append(basePackage, ".", f.getName().replace(".class", "")));
                }
            }
        });
    }


    private void doScan(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (isScanAnnotationPresent(clazz)) {
                AnnotationBeanDefinition beanDefinition = new StandardAnnotationBeanDefinition(clazz);
                AnnotationConfigUtils.processCommonAnnotation(beanDefinition);
                String name = nameGenerator.generateBeanName(beanDefinition, getRegistry());
                if (name != null) {
                    getRegistry().registryBeanDefinition(name, beanDefinition);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private boolean isScanAnnotationPresent(Class<?> clazz) {
        for (Class<? extends Annotation> clz : scanAnnotation) {
            if (clazz.isAnnotationPresent(clz)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    private void registerDefaultAnnotation() {
        addScanAnnotation(Component.class);
        addScanAnnotation(Controller.class);
        addScanAnnotation(Configuration.class);
        addScanAnnotation(Service.class);
    }

    public static void main(String[] args) {
        DefaultAutowiredCapableBeanFactory beanFactory = new DefaultAutowiredCapableBeanFactory();
        ClassPathBeanDefinitionScanner classPathScanner = new ClassPathBeanDefinitionScanner(beanFactory);
        classPathScanner.scan("com.masonluo.fastframework.test");
        System.out.println();
    }
}
