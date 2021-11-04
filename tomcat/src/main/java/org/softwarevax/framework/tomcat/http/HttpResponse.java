package org.softwarevax.framework.tomcat.http;

import org.softwarevax.framework.tomcat.http.enums.StatusCode;

public interface HttpResponse {

    /**
     * 获取协议名称
     * @return
     */
    String getProtocol();

    /**
     * 获取协议版本
     * @return
     */
    String getProtocolVersion();

    /**
     * 获取响应状态，含状态码和提示
     * @return
     */
    StatusCode getStatusCode();

    /**
     * 获取请求url
     * @return
     */
    String getUrl();

    /**
     * 获取响应的content-type
     * @return
     */
    String getContentType();

    /**
     * 获取响应体
     * @return
     */
    String getBody();
}
