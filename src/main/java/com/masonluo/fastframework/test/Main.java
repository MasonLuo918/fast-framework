package com.masonluo.fastframework.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.stream.Stream;

/**
 * @author masonluo
 * @date 2020/6/27 9:49 PM
 */
public class Main {
    public static void main(String[] args) {
        Constructor[] constructors = TestA.class.getConstructors();
        Stream.of(constructors).forEach(constructor -> {
            System.out.println("------------------------");
            System.out.println(constructor.getName());
            System.out.println(constructor.getParameterCount());
            System.out.println("Annotation: ");
            for (Annotation[] annotations : constructor.getParameterAnnotations()){
                Stream.of(annotations)
                        .map(annotation -> annotation.annotationType().getName())
                        .forEach(System.out::println);
            }
            System.out.println("------------------------");
        });
    }
}
