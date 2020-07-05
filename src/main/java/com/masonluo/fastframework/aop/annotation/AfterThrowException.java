package com.masonluo.fastframework.aop.annotation;

import java.lang.annotation.*;

/**
 * @author masonluo
 * @date 2020/7/5 10:51 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterThrowException {
    /**
     * ������ָʾ��
     */
    String value();
}
