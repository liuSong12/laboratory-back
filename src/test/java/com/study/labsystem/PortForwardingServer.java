package com.study.labsystem;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PortForwardingServer {

    public static void main(String[] args) {
        try {
            // 选择一个本地服务端口
            int localPort = 8080;

            // 获取公网 IP 地址和设置端口映射
            setupPortForwarding(localPort);

            // 启动本地服务
            startLocalServer(localPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setupPortForwarding(int localPort) throws Exception {
        GatewayDiscover discover = new GatewayDiscover();
        discover.discover();

        GatewayDevice d = discover.getValidGateway();

        if (d != null) {
            InetAddress localAddress = InetAddress.getLocalHost();
            d.addPortMapping(localPort, localPort, localAddress.getHostAddress(), "TCP", "PortForwarding");
            System.out.println("端口映射设置成功");
        } else {
            System.out.println("未找到合适的网关设备");
        }
    }

    private static void startLocalServer(int localPort) {
        try {
            ServerSocket serverSocket = new ServerSocket(localPort);
            System.out.println("等待公网连接...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("公网连接已建立");
                System.out.printf("----"+ clientSocket.getInetAddress().getHostAddress());

                // 处理客户端连接，可以在这里添加你的业务逻辑
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
