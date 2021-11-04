package org.softwarevax.framework.rpc.entity;

import java.util.List;

public class MethodEntity {

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

    private Object result;

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
