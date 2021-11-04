package org.softwarevax.framework.core;

public interface ApplicationContextEventAware extends Order {

    void onEvent(ApplicationContext ctx);
}
