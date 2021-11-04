package org.softwarevax.framework.rpc.service.consumer;

import org.softwarevax.framework.core.ApplicationContext;
import org.softwarevax.framework.core.SpringApplicationRunner;
import org.softwarevax.framework.core.annotations.SpringApplicationVax;

@SpringApplicationVax(packages = "org.softwarevax.framework.rpc.service.consumer")
public class ConsumerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplicationRunner.run(ConsumerApplication.class);
    }
}
