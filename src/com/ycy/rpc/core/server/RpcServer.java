package com.ycy.rpc.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer implements Runnable {
    private ServerSocket serverSocket;
    private int port;
    private boolean goon;
    private final RpcBeanFactory rpcBeanFactory;
    private static long executorId;

    public RpcServer() {
        goon = false;
        rpcBeanFactory = new RpcBeanFactory();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public RpcBeanFactory getRpcBeanFactory() {
        return rpcBeanFactory;
    }

    /*给RPC服务器注入接口，接口的具体实现类。*/
    public void registryRpc(Class<?> interfaces, Object object) {
        RpcBeanRegistry.registInterface(rpcBeanFactory, interfaces, object);
    }

    /*给RPC服务器注入接口，接口的具体实现类。*/
    public void registryRpc(Class<?> interfaces, Class<?> klass) {
        RpcBeanRegistry.registInterface(rpcBeanFactory, interfaces, klass);
    }

    /*启动Rpc服务器*/
    public void startRpcServer() throws IOException {
        if (this.port == 0) {
            return;
        }
        // 创建绑定到特定端口的服务器套接字。
        this.serverSocket = new ServerSocket(port);
        this.goon = true;
        new Thread(this, "RPC_SERVER").start();
    }

    /*关闭Rpc服务器*/
    private void stopRpcServer() {
        if (this.serverSocket != null && !this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.serverSocket = null;
            }
        }
    }

    @Override
    public void run() {
        while (goon) {
            try {
                // 侦听客户端的请求
                Socket client = serverSocket.accept();
                // 处理客户端的请求,RpcExecutor具体处理
                new RpcServerExecutor(client, this, ++executorId);
            } catch (IOException e) {
                goon = false;
                e.printStackTrace();
            }
        }
        stopRpcServer();
    }
}
