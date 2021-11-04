package org.softwarevax.framework.tomcat.http;

import org.softwarevax.framework.tomcat.http.enums.Method;

import java.util.Map;

public interface HttpRequest {

    /**
     * 获取请求方式
     * @return
     */
    Method getMethod();

    /**
     * 获取请求全路径
     * @return
     */
    String getUrl();

    /**
     * 获取请求资源
     * @return
     */
    String getUri();

    /**
     * 获取url上携带的参数
     * @return
     */
    Map<String, String> getQueryString();

    /**
     * 根据参数名，获取url上的参数值
     * @param paramName
     * @return
     */
    String getQueryString(String paramName);

    /**
     * 获取请求协议
     * @return
     */
    String getProtocol();

    /**
     * 获取协议版本
     * @return
     */
    String getProtocolVersion();

    /**
     * 获取请求头
     * @return
     */
    Map<String, String> getHeaders();

    /**
     * 获取请求头的某个值
     * @param headerName
     * @return
     */
    String getHeader(String headerName);

    /**
     * 获取请求类型
     * @return
     */
    String getContentType();

    /**
     * 获取请求数据
     * @return
     */
    String getBody();
}
