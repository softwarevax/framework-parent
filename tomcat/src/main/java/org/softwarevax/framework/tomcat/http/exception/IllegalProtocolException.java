package org.softwarevax.framework.tomcat.http.exception;

public class IllegalProtocolException extends RuntimeException {

    /**
     * 异常提示
     */
    private String msg;

    public IllegalProtocolException(String msg) {
        this.msg = msg;
    }
}
