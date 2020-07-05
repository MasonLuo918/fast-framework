package com.masonluo.fastframework.aop;

import java.lang.reflect.Method;

/**
 * @author masonluo
 * @date 2020/7/5 10:08 PM
 */
public interface JoinPoint {

    Object getThis();

    Object[] getArgs();

    Method getPointCut();
}
