package com.masonluo.fastframework.aop.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * @author masonluo
 * @date 2020/7/4 9:33 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aspect {
}
