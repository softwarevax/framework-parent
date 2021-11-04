package org.softwarevax.framework.rpc;

import org.softwarevax.framework.core.ApplicationContext;
import org.softwarevax.framework.core.SpringApplicationRunner;
import org.softwarevax.framework.core.annotations.SpringApplicationVax;

@SpringApplicationVax(packages = "org.softwarevax.framework")
public class RpcApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplicationRunner.run(RpcApplication.class);
    }
}
