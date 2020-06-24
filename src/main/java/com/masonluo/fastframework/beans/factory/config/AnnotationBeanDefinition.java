package com.masonluo.fastframework.beans.factory.config;

import com.masonluo.fastframework.core.meta.AnnotationMetaData;

/**
 * ��������ע�����ɵ�BeanDefinition
 *
 * @author masonluo
 * @date 2020/6/22 2:08 PM
 */
public interface AnnotationBeanDefinition extends BeanDefinition {
    /**
     * ���Ԫ������Ϣ
     */
    AnnotationMetaData getAnnotationMetaData();
}
