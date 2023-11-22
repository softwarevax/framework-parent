package org.softwarevax.framework.rpc;

import org.softwarevax.framework.context.ApplicationContext;
import org.softwarevax.framework.context.event.ApplicationContextEvent;
import org.softwarevax.framework.utils.GenericPackagesScanner;
import org.softwarevax.framework.utils.PackageScanner;

//@ComponentVax
public class RpcApplicationAware implements ApplicationContextEvent {

    private PackageScanner packageScanner = new GenericPackagesScanner(null);

    @Override
    public void onEvent(ApplicationContext ctx) {
        //packageScanner.setPackages(ctx.getPackages());
    }
}
