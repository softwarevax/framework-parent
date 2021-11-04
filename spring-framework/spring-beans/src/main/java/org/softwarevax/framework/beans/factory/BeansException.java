package org.softwarevax.framework.beans.factory;

/**
 * bean异常
 */
public class BeansException extends RuntimeException {

    /**
     * 异常信息
     */
    private String msg;

    public BeansException() {
        super();
    }

    /** 有详细提示的异常
     * @param msg 异常提示
     */
    public BeansException(String msg) {
        this.msg = msg;
    }
}
