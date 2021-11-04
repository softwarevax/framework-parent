package org.softwarevax.framework.tomcat.http.enums;

public enum StatusCode {

    /**
     * 协议非法
     */
    _450(450, "an invalid http protocol"),

    /**
     * 请求正常返回
     */
    _200(200, "OK");

    /**
     * 错误提示
     */
    private String errorMsg;

    /**
     * 状态码
     */
    private int code;

    StatusCode(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public String getMsg() {
        return this.errorMsg;
    }

    public int getCode() {
        return this.code;
    }
}
