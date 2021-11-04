package org.softwarevax.framework.mvc;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class MethodHandler {

    /**
     * 接口所在controller的实例
     */
    private Object obj;

    /**
     * controller全限定类名
     */
    private Class<?> clazz;

    /**
     * 接口对应的方法
     */
    private Method method;

    /**
     * 类url
     */
    private String clazzUrl;

    /**
     * 方法url
     */
    private String methodURL;

    /**
     * 全路径url
     */
    private String url;

    /**
     * method对应的参数名称，顺序
     */
    private List<String> parameters;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getClazzUrl() {
        return clazzUrl;
    }

    public void setClazzUrl(String clazzUrl) {
        this.clazzUrl = clazzUrl;
    }

    public String getMethodURL() {
        return methodURL;
    }

    public void setMethodURL(String methodURL) {
        this.methodURL = methodURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodHandler that = (MethodHandler) o;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
