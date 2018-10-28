package com.ycy.rpc.core.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcServerExecutor implements Runnable {
    private Socket socket;
    private RpcServer rpcServer;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public RpcServerExecutor(Socket socket, RpcServer rpcServer, long threadId) throws IOException {
        this.socket = socket;
        this.rpcServer = rpcServer;
        this.ois = new ObjectInputStream(this.socket.getInputStream());
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        new Thread(this, "RPC_EXECUTOR" + threadId).start();
    }

    public void closeSocket() {
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

    private void showParameters(Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            System.out.println(i + ":" + parameters[i]);
        }
    }

    @Override
    public void run() {
        try {
            // 接收Rpc客户端传递的rpcBeanId和参数
            String rpcBeanId = ois.readUTF();
            Object[] parameters = (Object[]) ois.readObject();
            showParameters(parameters);
            // 定位相关类，对象和方法
            RpcBeanDefination rpcBeanDefination;
            rpcBeanDefination = rpcServer.getRpcBeanFactory().getRpcBean(rpcBeanId);
            // 执行Rpc客户端要求执行的方法
            Method method = rpcBeanDefination.getMethod();
            Object object = rpcBeanDefination.getObject();
            Object result = method.invoke(object, parameters);
            // 向客户端返回执行结果
            oos.writeObject(result);
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeSocket();
        }
    }
}
