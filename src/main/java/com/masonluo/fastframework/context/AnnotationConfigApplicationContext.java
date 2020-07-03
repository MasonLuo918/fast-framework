package com.masonluo.fastframework.context;

import com.masonluo.fastframework.beans.factory.DefaultAutowiredCapableBeanFactory;
import com.masonluo.fastframework.beans.support.AnnotationConfigRegistry;
import com.masonluo.fastframework.beans.support.reader.AnnotationBeanDefinitionReader;
import com.masonluo.fastframework.beans.support.scanner.ClassPathBeanDefinitionScanner;

/**
 * // 解决扫描之后再次扫描到configuration
 * TODO 解决Component的循环扫描
 * @author masonluo
 * @date 2020/7/2 4:39 PM
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {
    private AnnotationBeanDefinitionReader reader = new AnnotationBeanDefinitionReader(this);

    private ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this);

    public AnnotationConfigApplicationContext() {

    }

    public AnnotationConfigApplicationContext(Class<?> clazz){
        register(clazz);
        refresh();
    }


    public AnnotationConfigApplicationContext(String basePackage){
        scan(basePackage);
        refresh();
    }

    public AnnotationConfigApplicationContext(AnnotationBeanDefinitionReader reader, ClassPathBeanDefinitionScanner scanner) {
        this.reader = reader;
        this.scanner = scanner;
    }

    public AnnotationConfigApplicationContext(DefaultAutowiredCapableBeanFactory beanFactory, AnnotationBeanDefinitionReader reader, ClassPathBeanDefinitionScanner scanner) {
        super(beanFactory);
        this.reader = reader;
        this.scanner = scanner;
    }

    @Override
    public void register(Class<?>... classes) {
        this.reader.register(classes);
    }

    @Override
    public void scan(String... basePackages) {
        for (String str : basePackages) {
            this.scanner.scan(str);
        }
    }
}
