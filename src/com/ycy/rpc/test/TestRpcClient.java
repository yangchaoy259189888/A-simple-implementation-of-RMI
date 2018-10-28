package com.ycy.rpc.test;

import com.ycy.rpc.core.client.RpcClient;
import com.ycy.rpc.test.interfaces.ITestForRPC;
import com.ycy.rpc.test.model.UserModel;

public class TestRpcClient {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient("127.0.0.1", 54189);
        ITestForRPC proxy = (ITestForRPC) rpcClient.getProxy(ITestForRPC.class);
        UserModel user = proxy.getUserById("123456");
        System.out.println(user);
    }
}
