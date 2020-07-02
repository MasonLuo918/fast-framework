package com.masonluo.fastframework.beans.support.scanner;

import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;

/**
 * @author masonluo
 * @date 2020/6/30 10:25 PM
 */
public interface Scanner {

    void scan(String basePackage);

    BeanDefinitionRegistry getRegistry();
}
