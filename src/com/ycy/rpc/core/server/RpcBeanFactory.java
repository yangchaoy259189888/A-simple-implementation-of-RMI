package com.ycy.rpc.core.server;

import java.util.HashMap;
import java.util.Map;

public class RpcBeanFactory {
    // 每个RPC服务器，应该有自己的rpcBeanMap
    private final Map<String, RpcBeanDefination> rpcBeanMap;

    RpcBeanFactory() {
        rpcBeanMap = new HashMap<>();
    }

    /*RPC服务器端，每个rpcBeanDefination中的方法都有特有的rpcBeanId，与RPC客户端相对应，这样才能远程调用*/
    void AddRpcBean(String rpcBeanId, RpcBeanDefination rpcBeanDefination) {
        if (rpcBeanMap.get(rpcBeanId) != null) {
            return;
        }
        rpcBeanMap.put(rpcBeanId, rpcBeanDefination);
    }

    RpcBeanDefination getRpcBean(String rpcBeanId) {
        return rpcBeanMap.get(rpcBeanId);
    }
}
