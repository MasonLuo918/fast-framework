package com.masonluo.fastframework.beans.factory.config;

import com.masonluo.fastframework.beans.MultiPropertyValues;
import com.masonluo.fastframework.core.AttributeAccessor;

/**
 * ��������һ��Bean
 *
 * @author masonluo
 * @date 2020/6/20 11:41 PM
 */
public interface BeanDefinition extends AttributeAccessor {

    void setLazyInit(boolean lazyInit);

    /**
     * �ж��Ƿ������أ�Ĭ��Ϊtrue
     */
    boolean isLazyInit();

    /**
     * ��ȡscope
     */
    String getScope();

    void setScope(String scope);

    /**
     * ����BeanName��Ҳ����Ψһid
     */
    String getBeanName();

    void setBeanName(String beanName);

    /**
     * bean����ȫ�޶���
     */
    String getBeanClassName();

    void setBeanClassName(String beanClassName);

    /**
     * �Ƿ�����ע�룬���Զ�ע���ʱ��
     * ������ֶ��ͬ���͵ģ���ô���Զ�ע�����ȵ�
     *
     * @return
     */
    boolean isPrimary();

    void setPrimary(boolean primary);

    /**
     * ��ȡԤ�����úõ��ֶ�����ֵ
     */
    MultiPropertyValues getPropertyValues();

    /**
     * ��ȡԤ�����õĹ��캯������ֵ
     *
     * @return
     */
    MultiPropertyValues getConstructorPropertyValues();
}