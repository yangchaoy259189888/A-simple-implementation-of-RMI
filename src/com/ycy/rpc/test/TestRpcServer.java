package com.ycy.rpc.test;

import com.ycy.rpc.core.server.RpcServer;
import com.ycy.rpc.test.interfaces.ITestForRPC;
import com.ycy.rpc.test.interfaces.UserAction;

import java.io.IOException;

public class TestRpcServer {
    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        rpcServer.setPort(54189);
        /*给RPC服务器端注入接口及其实现类，将来RPC客户端调用注入的实现类中的具体方法*/
        rpcServer.registryRpc(ITestForRPC.class, UserAction.class);
        try {
            rpcServer.startRpcServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
