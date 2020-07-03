package com.masonluo.fastframework;

import com.masonluo.fastframework.beans.annotation.ComponentScan;
import com.masonluo.fastframework.beans.annotation.Configuration;
import com.masonluo.fastframework.context.AnnotationConfigApplicationContext;
import com.masonluo.fastframework.test.Animal;

/**
 * @author masonluo
 * @date 2020/7/2 5:05 PM
 */
@Configuration
@ComponentScan(basePackages = "com.masonluo.fastframework.test")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(Main.class);
        System.out.println(configApplicationContext.getBean(Animal.class));
    }
}
