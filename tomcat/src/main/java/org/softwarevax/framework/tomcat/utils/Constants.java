package org.softwarevax.framework.tomcat.utils;

/**
 * http协议常量
 */
public class Constants {

    /**
     * http协议的行分隔符：\r\n
     */
    public static final String LINE_SPLIT = "\r\n";

    /**
     * http协议的请求数据分隔符：\r\n\r\n
     */
    public static final String BODY_SPLIT = "\r\n\r\n";

    /**
     * http协议版本
     */
    public static final String HTTP_VERSION = "HTTP/1.1";

    /**
     * http服务器名称
     */
    public static final String HTTP_SERVER_NAME = "Maple";

    /**
     * http默认content-Type类型
     */
    public static final String DEFAULT_CONTENT_TYPE = "text/html";

    /**
     * 属性分隔符
     */
    public static final String PROP_SPLIT = " ";

    /**
     * Content-Type key
     */
    public static final String CONTENT_TYPE_KEY = "Content-Type";

    /**
     * 默认Content-Type: 表单类型
     */
    public static final String DEFAULT_CONTENT_TYPE_KEY = "application/x-www-form-urlencoded";
}
