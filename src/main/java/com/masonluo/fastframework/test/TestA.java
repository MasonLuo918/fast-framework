package com.masonluo.fastframework.test;

import com.masonluo.fastframework.core.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

/**
 * @author masonluo
 * @date 2020/6/27 9:48 PM
 */
public class TestA {
    private int field1;

    private int field2;

    private String str;

    public TestA() {

    }

    public TestA(String str){
        this.str = str;
    }

    public TestA(int field1) {
        this.field1 = field1;
    }

    public TestA(@Autowired int field1, @Autowired int field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public static void main(String[] args) {
        Constructor[] constructors = TestA.class.getConstructors();
        for (Constructor constructor : constructors){
            Parameter[] parameters = constructor.getParameters();
            Stream.of(parameters)
                    .forEach(parameter -> System.out.println(parameter.getName() + " " + parameter.getType()));
        }
    }
}
