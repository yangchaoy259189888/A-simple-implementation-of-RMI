package com.ycy.rpc.core.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcClient {
    private RpcClientExecutor rpcClientExecutor;

    public RpcClient(String rpcServerIp, int rpcServerPort) {
        this.rpcClientExecutor = new RpcClientExecutor(rpcServerIp, rpcServerPort);
    }

    /*jdk代理*/
    public Object getProxy(Class<?> klass) {
        // 判断klass是否是接口，若不是，则应该采用cglib代理模式
        return Proxy.newProxyInstance(klass.getClassLoader(), new Class<?>[] { klass }, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String rpcBeanId = String.valueOf(method.toString().hashCode());
                Object result = rpcClientExecutor.rpcExecutor(rpcBeanId, args);

                return result;
            }
        });
    }
}
