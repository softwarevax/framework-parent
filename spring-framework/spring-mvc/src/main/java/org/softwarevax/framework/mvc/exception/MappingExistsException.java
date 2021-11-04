package org.softwarevax.framework.mvc.exception;

public class MappingExistsException extends RuntimeException {

    private String msg;

    public MappingExistsException() {
    }

    public MappingExistsException(String msg) {
        super(msg);
    }
}
