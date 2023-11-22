package org.softwarevax.framework.rpc.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import org.softwarevax.framework.context.ApplicationContext;
import org.softwarevax.framework.context.event.ApplicationContextEvent;
import org.softwarevax.framework.rpc.BootstrapClient;
import org.softwarevax.framework.rpc.entity.MethodEntity;
import org.softwarevax.framework.rpc.entity.ServiceConfig;
import org.softwarevax.framework.rpc.entity.ServiceEntity;
import org.softwarevax.framework.rpc.utils.NetWorkUtils;
import org.softwarevax.framework.utils.PropertyUtils;
import org.softwarevax.framework.utils.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractRpcProviderEventAware implements ApplicationContextEvent {

    public final static String RPC_CLASSPATH_NAME = "rpc.properties";

    @Override
    public void onEvent(ApplicationContext ctx) {
        try {
            List<Class<?>> registry = new ArrayList<>();
            Properties prop = PropertyUtils.getClassPathProperties(RPC_CLASSPATH_NAME);
            String serverUrl = (String) prop.get(Constants.SERVER_REGISTRY_URL);
            String packages = (String) prop.get(Constants.SERVER_REGISTRY_PACKAGE);
            String applicationName = (String) prop.get(Constants.APPLICATION_NAME);
            String applicationId = (String) prop.get(Constants.APPLICATION_ID);
            Iterator<Class<?>> iterator = null; //ctx.getClasses(null).iterator();
            while (iterator.hasNext()) {
                Class<?> next = iterator.next();
                String packageName = next.getPackage().getName();
                if(packageName.startsWith(packages)) {
                    registry.add(next);
                }
            }
            ServiceConfig config = new ServiceConfig();
            config.setHostName(NetWorkUtils.getIp());
            //config.setPort(ctx.getPort());
            config.setAppName(applicationName);
            config.setAppId(applicationId);
            List<ServiceEntity> services = new ArrayList<>();
            config.setServices(services);
            registry.stream().forEach(classes-> {
                ServiceEntity service = new ServiceEntity();
                service.setVersion(ServiceConfig.RPC_VERSION);
                service.setClazzName(classes.getName());
                List<MethodEntity> methods = new ArrayList<>();
                service.setMethods(methods);
                service.setInterfaceClazz(Arrays.stream(classes.getInterfaces()).map(row -> row.getName()).collect(Collectors.toList()));
                Class<?> clazz = ctx.getBean(classes.getName()).getClass();
                List<Method> methodList = Arrays.asList(clazz.getDeclaredMethods());
                methodList.stream().forEach(method -> {
                    MethodEntity methodEntity = new MethodEntity();
                    methodEntity.setMethodName(method.getName());
                    methodEntity.setRtnClazz(method.getReturnType().getName());
                    methodEntity.setParameterType(Arrays.asList(method.getParameterTypes()).stream().map(row -> row.getName()).collect(Collectors.toList()));
                    methods.add(methodEntity);
                });
                services.add(service);
            });
            new Thread(() -> {
                BootstrapClient client = new BootstrapClient(StringUtils.split(serverUrl, ":")[0], Integer.parseInt(StringUtils.split(serverUrl, ":")[1]));
                String request = HttpClient.postBuilder(serverUrl, Constants.REGISTRY_URL, JSON.toJSONString(config));
                Channel channel = client.getChannel();
                channel.write(request);
                channel.flush();
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return 9999;
    }
}
