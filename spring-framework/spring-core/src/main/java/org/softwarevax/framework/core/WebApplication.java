package org.softwarevax.framework.core;

public interface WebApplication {

    default int getPort() {
        return -1;
    }

    default void setPort(int port) {}
}
