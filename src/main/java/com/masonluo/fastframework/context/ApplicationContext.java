package com.masonluo.fastframework.context;

import com.masonluo.fastframework.beans.factory.ConfigurableBeanFactory;
import com.masonluo.fastframework.beans.factory.HierarchicalBeanFactory;
import com.masonluo.fastframework.utils.annotation.Nullable;

/**
 * @author masonluo
 * @date 2020/6/29 1:35 PM
 */
public interface ApplicationContext extends ConfigurableBeanFactory, HierarchicalBeanFactory {
    /**
     * Returns the unique id of application context
     *
     * @return
     */
    @Nullable
    String getId();

    @Nullable
    String getApplicationContextName();

    @Nullable
    String getParentApplicationContext();
}
