package org.softwarevax.framework.rpc.entity;

import java.util.List;

public class ServiceEntity {

    /**
     * 版本
     */
    private String version;

    /**
     * 全限定类名
     */
    private String clazzName;

    /**
     * 接口名
     */
    private List<String> interfaceClazz;

    /**
     * 方法
     */
    private List<MethodEntity> methods;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public List<MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodEntity> methods) {
        this.methods = methods;
    }

    public List<String> getInterfaceClazz() {
        return interfaceClazz;
    }

    public void setInterfaceClazz(List<String> interfaceClazz) {
        this.interfaceClazz = interfaceClazz;
    }
}
