package org.softwarevax.framework.context.event;

import org.softwarevax.framework.context.ApplicationContext;
import org.softwarevax.framework.context.Order;

public interface ApplicationContextEvent extends Order {

    void onEvent(ApplicationContext ctx);
}
