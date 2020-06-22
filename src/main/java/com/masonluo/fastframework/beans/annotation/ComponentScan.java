package com.masonluo.fastframework.beans.annotation;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/6/20 6:44 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {
    String[] basePackages() default "";
}
