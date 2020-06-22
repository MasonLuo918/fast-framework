package com.masonluo.fastframework.core.annotation;

import java.lang.annotation.*;

/**
 * ע���ڲ����ϵģ������Զ�ע�����ӦbeanName��bean
 *
 * �ڹ��캯����ʹ�ã�������У������޷�ʵ������
 * @author masonluo
 * @date 2020/6/21 12:25 AM
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    String value() default "";
}
