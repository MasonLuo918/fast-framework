package com.masonluo.fastframework.beans.stereotype;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/6/20 6:34 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}