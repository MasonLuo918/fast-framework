package com.masonluo.fastframework.aop.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author masonluo
 * @date 2020/7/5 9:31 PM
 */
public class SubjectHandler implements InvocationHandler {

    private Object target;

    public SubjectHandler(Object target){
        this.target = target;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object obj = method.invoke(target, args);
        System.out.println("After invoke");
        System.out.println(obj);
        return obj;
    }

    public static void main(String[] args) {
        Subject subject = new SubjectImpl();
        SubjectHandler handler = new SubjectHandler(subject);
        Subject proxy = (Subject) handler.getProxy();
        proxy.sayHello();
        proxy.add(1, 2);
    }
}
