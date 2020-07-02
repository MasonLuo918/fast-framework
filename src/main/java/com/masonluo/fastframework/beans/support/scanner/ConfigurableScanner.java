package com.masonluo.fastframework.beans.support.scanner;

import java.lang.annotation.Annotation;

/**
 * @author masonluo
 * @date 2020/6/30 10:27 PM
 */
public interface ConfigurableScanner extends Scanner {

    <T extends Annotation> void addScanAnnotation(Class<T> annotation);

    <T extends Annotation> void removeScanAnnotation(Class<T> annotation);
}
