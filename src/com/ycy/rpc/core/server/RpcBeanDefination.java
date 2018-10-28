package com.ycy.rpc.core.server;

import java.lang.reflect.Method;

/*RPC服务器端的远程类，方法，对象定义*/
public class RpcBeanDefination {
    private Class<?> klass;
    private Method method;
    private Object object;

    public RpcBeanDefination() {
    }

    public Class<?> getKlass() {
        return klass;
    }

    public void setKlass(Class<?> klass) {
        this.klass = klass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
