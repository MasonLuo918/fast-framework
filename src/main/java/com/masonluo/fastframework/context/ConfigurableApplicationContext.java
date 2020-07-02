package com.masonluo.fastframework.context;

import com.masonluo.fastframework.beans.factory.ConfigurableAutowireCapableBeanFactory;

/**
 * @author masonluo
 * @date 2020/7/2 10:37 AM
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    void setId(String id);

    void setApplicationContextName(String applicationContextName);

    void setParentApplicationContext(ApplicationContext context);

    ConfigurableAutowireCapableBeanFactory getBeanFactory();

    void refresh();
}
