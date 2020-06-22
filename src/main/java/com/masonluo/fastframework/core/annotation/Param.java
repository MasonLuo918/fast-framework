package com.masonluo.fastframework.core.annotation;

import java.lang.annotation.*;

/**
 * 注解在参数上的，请求自动注入相对应beanName的bean
 *
 * 在构造函数上使用，必须带有，否则无法实例化类
 * @author masonluo
 * @date 2020/6/21 12:25 AM
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    String value() default "";
}
