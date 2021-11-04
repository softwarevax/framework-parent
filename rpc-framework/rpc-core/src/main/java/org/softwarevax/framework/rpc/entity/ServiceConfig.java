package org.softwarevax.framework.rpc.entity;

import java.util.List;

public class ServiceConfig {

    public static final String RPC_VERSION = "1.1";

    /**
     * 目的主机hostname
     */
    private String hostName;

    /**
     * 目的主机端口
     */
    private int port;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用名称，相同的视为一个应用
     */
    private String appId;

    /**
     * 应用
     */
    private List<ServiceEntity> services;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ServiceEntity> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceConfig config = (ServiceConfig) o;

        if (port != config.port) return false;
        if (!hostName.equals(config.hostName)) return false;
        return appId.equals(config.appId);
    }

    @Override
    public int hashCode() {
        int result = hostName.hashCode();
        result = 31 * result + port;
        result = 31 * result + appId.hashCode();
        return result;
    }
}
