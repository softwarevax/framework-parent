package org.softwarevax.framework.rpc.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetWorkUtils {

    /**
     * 获取端口
     * @param port
     * @return
     */
    public static int getPort(Integer port) {
        Assert.isTrue(port == null || (port > 0 && port < 65535), "端口不合法");
        if(port == null) {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(0);
                return serverSocket.getLocalPort();
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return port;
    }

    /**
     * 获取可用端口
     * @return
     */
    public static int getPort() {
        return getPort(null);
    }

    public static String getIp() {
        Enumeration<?> netInterfaces;
        List<NetworkInterface> netlist = new ArrayList<>();
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                if(ni.isLoopback()) {
                    continue;
                }
                netlist.add(0, ni);
            }
            for(NetworkInterface ni : netlist) {
                Enumeration<?> cardipaddress = ni.getInetAddresses();
                while (cardipaddress.hasMoreElements()) {
                    InetAddress ip = (InetAddress) cardipaddress.nextElement();
                    return ip.getLocalHost().getHostAddress();
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getIp());
    }
}
