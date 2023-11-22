package org.softwarevax.framework.context;

public interface Order {

    default int getOrder() {
        return 9999;
    }
}
