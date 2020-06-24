package com.masonluo.fastframework.beans.support.reader;

import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import org.junit.Test;

/**
 * @author masonluo
 * @date 2020/6/22 4:14 PM
 */
public class AnnotationBeanDefinitionReaderTest {

    @Test
    public void testRegisterBean(){
        AnnotationBeanDefinitionReader reader = new AnnotationBeanDefinitionReader(new BeanDefinitionRegistry() {
            @Override
            public void registryBeanDefinition(String beanName, BeanDefinition definition) {

            }

            @Override
            public BeanDefinition removeBeanDefinition(String beanName) {
                return null;
            }

            @Override
            public int getBeanDefinitionCount() {
                return 0;
            }

            @Override
            public String[] getBeanDefinitionNames() {
                return new String[0];
            }

            @Override
            public boolean containsBeanDefinition(String beanName) {
                return false;
            }
        });

        reader.register(TestClass.class);
        System.out.println();
    }

}