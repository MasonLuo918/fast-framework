package com.masonluo.fastframework.utils.annotation;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/6/29 1:39 PM
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nullable {
    String value() default "";

    String description() default "This means that the method can returns null or the field can be null";
}
