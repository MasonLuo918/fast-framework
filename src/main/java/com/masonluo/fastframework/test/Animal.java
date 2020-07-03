package com.masonluo.fastframework.test;

import com.masonluo.fastframework.beans.stereotype.Component;
import com.masonluo.fastframework.core.annotation.Lazy;

/**
 * @author masonluo
 * @date 2020/7/2 5:03 PM
 */
@Component
@Lazy
public class Animal {
    private String name;

    private String describe;
}
