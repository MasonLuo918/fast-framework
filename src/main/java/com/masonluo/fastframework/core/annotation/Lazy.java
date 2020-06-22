package com.masonluo.fastframework.core.annotation;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/6/22 4:16 PM
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lazy {
    boolean value() default true;
}
