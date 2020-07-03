package com.masonluo.fastframework.beans.annotation;

import java.lang.annotation.*;

/**
 * ע���ڷ����ϣ� ��������һ��Beanע����Ioc������
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
