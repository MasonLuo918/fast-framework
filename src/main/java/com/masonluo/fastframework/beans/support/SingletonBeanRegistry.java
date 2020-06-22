package com.masonluo.fastframework.beans.support;

/**
 * ����ע����� Ϊʲôû��ԭ�͵�ע����أ�
 * <p>
 * ��Ϊԭ����Ioc�����в������л���
 *
 * @author masonluo
 * @date 2020/6/20 6:50 PM
 */
public interface SingletonBeanRegistry {

    /**
     * ע��һ������
     */
    void registerSingleton(String beanName, Object singletonBean);

    /**
     * ͨ��beanName��ȡһ������
     * <p>
     * beanName��Ԥ�����beanName��������Ĭ�����շ���򣬲�������ĸСд��ɵ�beanName
     */
    Object getSingleton(String beanName);

    /**
     * �ж��Ƿ����һ�������ڻ�����
     */
    boolean containsSingleton(String beanName);

    /**
     * ��ȡ�����ĸ���
     */
    int getSingletonCount();

    /**
     * ��ȡ���е���������
     */
    String[] getSingletonNames();
}
