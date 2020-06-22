package com.masonluo.fastframework.beans.stereotype;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/6/22 2:30 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    String value() default "";
}
