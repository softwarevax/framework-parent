package org.softwarevax.framework.rpc.registry.controller;

import org.softwarevax.framework.beans.annotation.ControllerVax;
import org.softwarevax.framework.mvc.annotations.UrlMapping;
import org.softwarevax.framework.rpc.entity.ServiceConfig;
import org.softwarevax.framework.rpc.entity.ServiceEntity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@ControllerVax
@UrlMapping("/registry")
public class RegistryController {

    private Set<ServiceConfig> services = new HashSet<>();

    @UrlMapping("/service")
    public boolean service(ServiceConfig service) {
        services.add(service);
        return true;
    }

    @UrlMapping("/available")
    public ServiceConfig getService(String clazz) {
        if(services.isEmpty()) {
            return null;
        }
        HashSet<ServiceConfig> serviceConfigs = new HashSet<>(services);
        Iterator<ServiceConfig> configIt = serviceConfigs.iterator();
        ServiceConfig returnConfig = null;
        while (configIt.hasNext()) {
            ServiceConfig config = configIt.next();
            List<ServiceEntity> services = config.getServices();
            Iterator<ServiceEntity> serviceIt = services.iterator();
            while (serviceIt.hasNext()) {
                ServiceEntity service = serviceIt.next();
                List<String> interfaceClazz = service.getInterfaceClazz();
                if(!interfaceClazz.contains(clazz)) {
                    serviceIt.remove();
                } else {
                    returnConfig = config;
                }
            }
        }
        return returnConfig;
    }
}
