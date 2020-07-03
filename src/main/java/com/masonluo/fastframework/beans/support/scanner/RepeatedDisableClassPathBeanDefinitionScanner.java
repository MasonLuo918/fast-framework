package com.masonluo.fastframework.beans.support.scanner;

import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author masonluo
 * @date 2020/7/3 5:42 PM
 */
public class RepeatedDisableClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private Set<String> packageHasBennScanned;

    public RepeatedDisableClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this(registry, Collections.newSetFromMap(new ConcurrentHashMap<>()));
    }

    public RepeatedDisableClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, Set<String> packageHasBeenScanned) {
        super(registry);
        this.packageHasBennScanned = packageHasBeenScanned;
    }

    private boolean isPackageHasBennScanned(String basePackage) {
        if (packageHasBennScanned.contains(basePackage)) {
            return true;
        }
        for (String string : packageHasBennScanned) {
            if (string.startsWith(basePackage)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void scan(String basePackageStr) {
        if (isPackageHasBennScanned(basePackageStr)) {
            return;
        }
        packageHasBennScanned.add(basePackageStr);
        super.scan(basePackageStr);
    }

    public void scan(String... packages) {
        for (String str : packages) {
            scan(str);
        }
    }
}
