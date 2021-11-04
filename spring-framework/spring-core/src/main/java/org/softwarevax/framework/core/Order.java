package org.softwarevax.framework.core;

public interface Order {

    default int getOrder() {
        return 9999;
    }
}
