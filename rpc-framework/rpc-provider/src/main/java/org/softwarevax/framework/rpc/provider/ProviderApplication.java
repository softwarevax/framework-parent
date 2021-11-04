package org.softwarevax.framework.rpc.provider;

import org.softwarevax.framework.core.ApplicationContext;
import org.softwarevax.framework.core.SpringApplicationRunner;
import org.softwarevax.framework.core.annotations.SpringApplicationVax;

@SpringApplicationVax(packages = "org.softwarevax.framework.rpc.provider")
public class ProviderApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplicationRunner.run(ProviderApplication.class);
    }
}
