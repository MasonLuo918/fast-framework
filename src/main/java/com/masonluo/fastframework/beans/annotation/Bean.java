package com.masonluo.fastframework.beans.annotation;

import java.lang.annotation.*;

/**
 * 注解在方法上， 可以生成一个Bean注册入Ioc容器中
 *
 * @author masonluo
 * @date 2020/6/20 6:42 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    String value() default "";
}
