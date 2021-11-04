package org.softwarevax.framework.tomcat.http;

import lombok.Data;
import lombok.experimental.Accessors;
import org.softwarevax.framework.tomcat.http.enums.Method;

import java.util.Map;

@Data
@Accessors(chain = true)
public class DefaultHttpRequest implements HttpRequest {

    /**
     * 请求方法
     */
    private Method method;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 资源地址
     */
    private String uri;

    /**
     * url参数
     */
    private Map<String, String> queryString;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 协议版本
     */
    private String protocolVersion;

    /**
     * 头部
     */
    private Map<String, String> headers;

    /**
     * contentType
     */
    private String contentType;

    /**
     * 请求体
     */
    private String body;

    @Override
    public String getQueryString(String paramName) {
        return this.queryString.get(paramName);
    }

    /**
     * 获取请求头的某个值
     * @param headerName
     * @return
     */
    @Override
    public String getHeader(String headerName) {
        return this.headers.get(headerName);
    }
}
