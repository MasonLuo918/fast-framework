package com.masonluo.fastframework.aop.annotation;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/7/5 10:51 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterReturning {
    /**
     * 切入点的指示器
     */
    String value();
}
