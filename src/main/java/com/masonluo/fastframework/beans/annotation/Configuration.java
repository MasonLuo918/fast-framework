package com.masonluo.fastframework.beans.annotation;

import java.lang.annotation.*;

/**
 * ����һ��������
 *
 * @author masonluo
 * @date 2020/6/20 6:43 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
    String value() default "";
}
