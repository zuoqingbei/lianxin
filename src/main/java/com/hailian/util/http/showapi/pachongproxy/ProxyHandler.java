package com.hailian.util.http.showapi.pachongproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hailian.modules.admin.ordermanager.model.PachongLog;

public class ProxyHandler implements InvocationHandler {
    private Object targetObject;//被代理的对象
    public  Object newProxyInstance(Object targetObject){
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),targetObject.getClass().getInterfaces(),this);
    }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String argsList = "未知";
        String url = "未知";
        String methodName = "方法名称："+method.getName();
        Date date = new Date();
        try {
            argsList = "参数列表："+StringUtils.join(args, " ");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            url =  "访问地址："+method.getAnnotation(URLAnnotation.class).value();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            PachongLog log = new PachongLog();
            log.set("create_time",date);
            log.set("parameters",argsList);
            log.set("url",url);
            log.set("method",methodName);
            log.save();
        }catch (Exception e){
            e.printStackTrace();
        }

      /*  System.out.println(argsList);
        System.out.println(methodName);
        System.out.println(url);*/

        return method.invoke(targetObject,args);
    }
}
