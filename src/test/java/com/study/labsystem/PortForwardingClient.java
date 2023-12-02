package com.study.labsystem;

import java.net.InetAddress;
import java.net.Socket;

public class PortForwardingClient {

    public static void main(String[] args) {
        try {
            // 设置公网服务器地址和端口
            String serverAddress = "127.0.0.1";
            int serverPort = 8080;

            // 连接公网服务器
            connectToPublicServer(serverAddress, serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void connectToPublicServer(String serverAddress, int serverPort) throws Exception {
        Socket socket = new Socket(InetAddress.getByName(serverAddress), serverPort);
        System.out.println("连接到公网服务器");

        // 处理与公网服务器的连接，可以在这里添加你的业务逻辑
    }
}
