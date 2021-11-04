package org.softwarevax.framework.rpc.entity;

import java.util.List;

public class RpcEntity {

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
     * 版本
     */
    private String version;

    /**
     * 全限定类名
     */
    private String clazzName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法返回类型全限定名
     */
    private String rtnClazz;

    /**
     * 参数类型全限定名
     */
    private List<String> parameterType;

    /**
     * 参数值
     */
    private List<String> parameterVal;

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

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRtnClazz() {
        return rtnClazz;
    }

    public void setRtnClazz(String rtnClazz) {
        this.rtnClazz = rtnClazz;
    }

    public List<String> getParameterType() {
        return parameterType;
    }

    public void setParameterType(List<String> parameterType) {
        this.parameterType = parameterType;
    }

    public List<String> getParameterVal() {
        return parameterVal;
    }

    public void setParameterVal(List<String> parameterVal) {
        this.parameterVal = parameterVal;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
