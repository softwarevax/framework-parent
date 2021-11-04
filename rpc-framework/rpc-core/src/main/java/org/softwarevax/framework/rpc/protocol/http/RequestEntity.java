package org.softwarevax.framework.rpc.protocol.http;

import java.util.Map;

public class RequestEntity {

    /**
     * 请求方法
     */
    private String methodName;

    /**
     * 请求路径
     */
    private String uri;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 协议版本
     */
    private String version;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 请求/响应体内容
     */
    private String contentType;

    /**
     * url携带的参数
     */
    private Map<String, String> queryString;

    /**
     * 请求体内容
     */
    private Object body;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public void setQueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
