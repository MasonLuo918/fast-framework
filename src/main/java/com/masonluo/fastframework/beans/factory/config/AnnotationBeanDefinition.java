package com.masonluo.fastframework.beans.factory.config;

import com.masonluo.fastframework.core.meta.AnnotationMetaData;

/**
 * 用来描述注解生成的BeanDefinition
 *
 * @author masonluo
 * @date 2020/6/22 2:08 PM
 */
public interface AnnotationBeanDefinition extends BeanDefinition {
    /**
     * 存放元数据信息
     */
    AnnotationMetaData getAnnotationMetaData();
}
