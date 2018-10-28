package com.ycy.rpc.core.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClientExecutor {
    private String rpcServerIp;
    private int rpcServerPort;

    RpcClientExecutor() {
    }

    RpcClientExecutor(String rpcServerIp, int rpcServerPort) {
        this.rpcServerIp = rpcServerIp;
        this.rpcServerPort = rpcServerPort;
    }

    String getRpcServerIp() {
        return rpcServerIp;
    }

    void setRpcServerIp(String rpcServerIp) {
        this.rpcServerIp = rpcServerIp;
    }

    int getRpcServerPort() {
        return rpcServerPort;
    }

    void setRpcServerPort(int rpcServerPort) {
        this.rpcServerPort = rpcServerPort;
    }

    void closeSocket(ObjectInputStream ois, ObjectOutputStream oos, Socket socket) {
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ois = null;
            }
        }
        if (oos != null) {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                oos = null;
            }
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket = null;
            }
        }
    }

    <T> T rpcExecutor(String rpcBeanId, Object[] para) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(rpcServerIp, rpcServerPort);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        // 给RPC服务器发送rpcBeanId和参数
        oos.writeUTF(rpcBeanId);
        oos.writeObject(para);
        // 接收RPC服务器执行的结果，并返回
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object result = ois.readObject();

        closeSocket(ois, oos, socket);

        return (T) result;
    }
}
