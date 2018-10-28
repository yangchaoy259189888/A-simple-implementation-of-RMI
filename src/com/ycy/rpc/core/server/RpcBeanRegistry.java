package com.ycy.rpc.core.server;

import java.lang.reflect.Method;

public class RpcBeanRegistry {
    RpcBeanRegistry() {
    }

    static void doRegist(RpcBeanFactory rpcBeanFactory, Class<?> interfaces, Object object) {
        Method[] methods  = interfaces.getDeclaredMethods();
        for (Method method : methods) {
            String rpcBeanId = String.valueOf(method.toString().hashCode());
            RpcBeanDefination rpcBeanDefination = new RpcBeanDefination();
            rpcBeanDefination.setKlass(interfaces);
            rpcBeanDefination.setMethod(method);
            rpcBeanDefination.setObject(object);

            rpcBeanFactory.AddRpcBean(rpcBeanId, rpcBeanDefination);
        }
    }

    /*给RPC服务器注入接口，接口的具体实现类。*/
    static void registInterface(RpcBeanFactory rpcBeanFactory, Class<?> interfaces, Object object) {
        if (!interfaces.isAssignableFrom(object.getClass())) {
            return;
        }
        doRegist(rpcBeanFactory, interfaces, object);
    }

    /*给RPC服务器注入接口，接口的具体实现类。*/
    static void registInterface(RpcBeanFactory rpcBeanFactory, Class<?> interfaces, Class<?> klass) {
        if (!interfaces.isAssignableFrom(klass)) {
            return;
        }
        try {
            doRegist(rpcBeanFactory, interfaces, klass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /*给RPC客户端注入接口。*/
    static void registInterface(RpcBeanFactory rpcBeanFactory, Class<?> interfaces) {
        doRegist(rpcBeanFactory, interfaces, null);
    }
}
