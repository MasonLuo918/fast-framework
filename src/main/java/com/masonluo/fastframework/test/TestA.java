package com.masonluo.fastframework.test;

import com.masonluo.fastframework.core.annotation.Autowired;

/**
 * @author masonluo
 * @date 2020/6/27 9:48 PM
 */
public class TestA {
    private int field1;

    private int field2;

    public TestA() {

    }

    public TestA(int field1) {
        this.field1 = field1;
    }

    public TestA(@Autowired int field1, @Autowired int field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
