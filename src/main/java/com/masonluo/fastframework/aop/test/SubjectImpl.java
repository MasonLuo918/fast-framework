package com.masonluo.fastframework.aop.test;

/**
 * @author masonluo
 * @date 2020/7/5 9:31 PM
 */
public class SubjectImpl implements Subject {
    @Override
    public void sayHello() {
        System.out.println("Hello");
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
