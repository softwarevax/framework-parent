package org.softwarevax.framework.rpc;

import org.softwarevax.framework.beans.annotation.ComponentVax;
import org.softwarevax.framework.core.ApplicationContext;
import org.softwarevax.framework.core.ApplicationContextEventAware;
import org.softwarevax.framework.utils.GenericPackagesScanner;
import org.softwarevax.framework.utils.PackageScanner;

//@ComponentVax
public class RpcApplicationAware implements ApplicationContextEventAware {

    private PackageScanner packageScanner = new GenericPackagesScanner();

    @Override
    public void onEvent(ApplicationContext ctx) {
        packageScanner.setPackages(ctx.getPackages());
    }
}
