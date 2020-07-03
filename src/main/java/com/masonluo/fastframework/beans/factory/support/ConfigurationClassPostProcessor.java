package com.masonluo.fastframework.beans.factory.support;

import com.masonluo.fastframework.beans.annotation.ComponentScan;
import com.masonluo.fastframework.beans.annotation.Configuration;
import com.masonluo.fastframework.beans.factory.ConfigurableBeanFactory;
import com.masonluo.fastframework.beans.factory.config.AnnotationBeanDefinition;
import com.masonluo.fastframework.beans.factory.config.BeanDefinition;
import com.masonluo.fastframework.beans.support.BeanDefinitionRegistry;
import com.masonluo.fastframework.beans.support.scanner.RepeatedDisableClassPathBeanDefinitionScanner;
import com.masonluo.fastframework.beans.support.scanner.Scanner;
import com.masonluo.fastframework.utils.Assert;
import com.masonluo.fastframework.utils.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @author masonluo
 * @date 2020/7/2 2:17 PM
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        Set<String> packageHasBeenScanned = new HashSet<>();
        RepeatedDisableClassPathBeanDefinitionScanner scanner =
                new RepeatedDisableClassPathBeanDefinitionScanner(registry, packageHasBeenScanned);
        Queue<ComponentScanClass> componentScanQueue = getComponentScanClass(registry);
        while (!componentScanQueue.isEmpty()) {
            ComponentScanClass componentScanClass = componentScanQueue.remove();
            scanner.scan(componentScanClass.basePackage);
            findComponentScanClass(componentScanQueue, componentScanClass, packageHasBeenScanned);
        }
    }

    private void findComponentScanClass(Queue<ComponentScanClass> componentScanQueue,
                                        ComponentScanClass scanClass,
                                        Set<String> packageHasBeenScanned) {
        String[] packages = scanClass.basePackage;
        for (String basePackage : packages) {
            List<ComponentScanClass> scanClasses = doFindComponentScanClass(basePackage, packageHasBeenScanned);
            scanClasses.remove(scanClass);
            componentScanQueue.addAll(scanClasses);
        }
    }

    private List<ComponentScanClass> doFindComponentScanClass(String basePackage, Set<String> packageHasBeenScanned) {
        List<ComponentScanClass> list = new ArrayList<>();
        String path = StringUtils.packageToPath(basePackage);
        URL url = getClass().getClassLoader().getResource(path);
        if (url == null) {
            throw new IllegalArgumentException("package does not exist");
        }
        File file = new File(url.getFile());
        File[] listFile = file.listFiles();
        if (listFile == null || listFile.length == 0) {
            return list;
        }
        for (File f : listFile) {
            if (f.isDirectory()) {
                list.addAll(doFindComponentScanClass(basePackage + "." + f.getName(), packageHasBeenScanned));
            } else if (f.getName().endsWith(".class")) {
                ComponentScanClass componentScanClass = doResolveClassFile(basePackage + "." + f.getName().replace(".class", ""), packageHasBeenScanned);
                if (componentScanClass != null) {
                    list.add(componentScanClass);
                }
            }
        }
        return list;
    }

    private ComponentScanClass doResolveClassFile(String className, Set<String> packageHasBennScanned) {
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(Configuration.class) && clazz.isAnnotationPresent(ComponentScan.class)) {
                ComponentScan componentScan = (ComponentScan) clazz.getAnnotation(ComponentScan.class);
                String[] basePackages = componentScan.basePackages();
                basePackages = removeHasBeenScannedPackage(basePackages, packageHasBennScanned);
                return new ComponentScanClass(basePackages, className, null);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] removeHasBeenScannedPackage(String[] basePackages, Set<String> packageHasBennScanned) {
        if (basePackages == null || basePackages.length == 0) {
            return basePackages;
        }
        List<String> packages = new ArrayList<>();
        for (String str : basePackages) {
            if (!isPackageHasBennScanned(str, packageHasBennScanned)) {
                packages.add(str);
            }
        }
        return packages.toArray(new String[0]);
    }

    private boolean isPackageHasBennScanned(String basePackage, Set<String> packageHasBennScanned) {
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
    public void postProcessBeanFactory(ConfigurableBeanFactory beanFactory) {
        // do nothing
    }

    private Queue<ComponentScanClass> getComponentScanClass(BeanDefinitionRegistry registry) {
        Queue<ComponentScanClass> componentScanClasses = new LinkedList<>();
        Map<String, BeanDefinition> definitionMap = registry.getBeanDefinitions();
        for (Map.Entry<String, BeanDefinition> definitionEntry : definitionMap.entrySet()) {
            BeanDefinition definition = definitionEntry.getValue();
            if (definition instanceof AnnotationBeanDefinition) {
                resolveAnnotationBeanDefinition(componentScanClasses, (AnnotationBeanDefinition) definition);
            }
        }
        return componentScanClasses;
    }


    private void resolveAnnotationBeanDefinition(Queue<ComponentScanClass> componentScanClasses, AnnotationBeanDefinition definition) {
        Assert.notNull(componentScanClasses);
        Assert.notNull(definition);
        if (definition.getAnnotationMetaData().hasAnnotation(Configuration.class)) {
            ComponentScanClass componentScanClass = ComponentScanClass.newInstance(definition);
            if (componentScanClass != null) {
                componentScanClasses.offer(componentScanClass);
            }
        }
    }

    static class ComponentScanClass {
        private final String[] basePackage;

        private final String beanClassName;

        private final String beanName;

        public ComponentScanClass(String[] basePackage, String beanClassName, String beanName) {
            this.basePackage = basePackage;
            this.beanClassName = beanClassName;
            this.beanName = beanName;
        }

        public static ComponentScanClass newInstance(AnnotationBeanDefinition definition) {
            if (definition.getAnnotationMetaData().hasAnnotation(ComponentScan.class)) {
                ComponentScan componentScan = (ComponentScan) definition.getAnnotationMetaData().getAnnotation(ComponentScan.class);
                String[] basePackages = componentScan.basePackages();
                String beanClassName = definition.getBeanClassName();
                String beanName = definition.getBeanName();
                return new ComponentScanClass(basePackages, beanClassName, beanName);
            }
            return null;
        }

        public String[] getBasePackage() {
            return basePackage;
        }

        public String getBeanClassName() {
            return beanClassName;
        }

        public String getBeanName() {
            return beanName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ComponentScanClass)) return false;

            ComponentScanClass scanClass = (ComponentScanClass) o;

            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(getBasePackage(), scanClass.getBasePackage())) return false;
            if (getBeanClassName() != null ? !getBeanClassName().equals(scanClass.getBeanClassName()) : scanClass.getBeanClassName() != null)
                return false;
            return getBeanName() != null ? getBeanName().equals(scanClass.getBeanName()) : scanClass.getBeanName() == null;
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(getBasePackage());
            result = 31 * result + (getBeanClassName() != null ? getBeanClassName().hashCode() : 0);
            result = 31 * result + (getBeanName() != null ? getBeanName().hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ComponentScanClass{" +
                    "basePackage=" + Arrays.toString(basePackage) +
                    ", beanClassName='" + beanClassName + '\'' +
                    ", beanName='" + beanName + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<ComponentScanClass> list = new ArrayList<>();
        ComponentScanClass one = new ComponentScanClass(new String[]{"hello"}, "com.masonluo", "hello");
        ComponentScanClass two = new ComponentScanClass(new String[]{"hello"}, "com.masonluo", "hello");
        list.add(one);
        list.remove(two);
        System.out.println();
    }

}
