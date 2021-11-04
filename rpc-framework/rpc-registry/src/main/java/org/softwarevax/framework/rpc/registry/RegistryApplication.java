package org.softwarevax.framework.rpc.registry;

import org.softwarevax.framework.core.SpringApplicationRunner;
import org.softwarevax.framework.core.annotations.SpringApplicationVax;

@SpringApplicationVax(packages = "org.softwarevax.framework.rpc.registry")
public class RegistryApplication {

    public static void main(String[] args) {
        SpringApplicationRunner.run(RegistryApplication.class);
    }
}
