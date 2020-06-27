package com.masonluo.fastframework.core.annotation;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/6/27 2:10 PM
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
