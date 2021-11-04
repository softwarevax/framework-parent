package org.softwarevax.framework.rpc.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.softwarevax.framework.beans.factory.BeanDefinition;
import org.softwarevax.framework.core.ApplicationContext;
import org.softwarevax.framework.core.ApplicationContextEventAware;
import org.softwarevax.framework.rpc.BootstrapClient;
import org.softwarevax.framework.rpc.entity.ServiceConfig;
import org.softwarevax.framework.rpc.protocol.ClientHandler;
import org.softwarevax.framework.rpc.utils.ProxyUtils;
import org.softwarevax.framework.utils.ClassUtils;
import org.softwarevax.framework.utils.PropertyUtils;
import org.softwarevax.framework.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractRpcConsumerEventAware implements ApplicationContextEventAware {

    public final static String RPC_CLASSPATH_NAME = "rpc.properties";

    @Override
    public void onEvent(ApplicationContext ctx) {
        try {
            Properties prop = PropertyUtils.getClassPathProperties(RPC_CLASSPATH_NAME);
            String serverUrl = (String) prop.get(Constants.SERVER_REGISTRY_URL);
            String packages = (String) prop.get(Constants.SERVER_REGISTRY_PACKAGE);
            List<Class<?>> registry = new ArrayList<>();
            Iterator<Class<?>> iterator = ctx.getClasses(null).iterator();
            while (iterator.hasNext()) {
                Class<?> next = iterator.next();
                String packageName = next.getPackage().getName();
                if(!packageName.startsWith(packages)) {
                    continue;
                }
                registry.add(next);
            }
            Iterator<Class<?>> clazzIt = registry.iterator();
            List<BeanDefinition> dependOns = new ArrayList<>();
            while (clazzIt.hasNext()) {
                Class<?> clazz = clazzIt.next();
                BeanDefinition definition = ctx.getDefinition(clazz.getName());
                dependOns.add(definition);
            }
            List<String> dependOnClazz = dependOns.stream().flatMap(row -> row.getDependsOn().stream()).map(row -> row.getBeanName()).collect(Collectors.toList());
            new Thread(() -> {
                BootstrapClient client = new BootstrapClient(StringUtils.split(serverUrl, ":")[0], Integer.parseInt(StringUtils.split(serverUrl, ":")[1]));
                dependOnClazz.stream().forEach(clazz -> {
                    Map<String, String> form = new HashMap<>();
                    form.put("clazz", clazz);
                    String request = HttpClient.postFormBuilder(serverUrl, Constants.AVAILABLE_URL, form);
                    Channel channel = client.getChannel();
                    ClientHandler clientHandler = new ClientHandler() {
                        public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
                            context.fireChannelRead(msg);
                            String body = HttpClient.getFormBody((String) msg);
                            ServiceConfig config = JSON.parseObject(body, ServiceConfig.class);
                            ctx.registry(clazz, ProxyUtils.getProxy(ClassUtils.loadClass(clazz), new RpcProxyFactory(config)));
                        }
                    };
                    channel.pipeline().addLast(clientHandler);
                    channel.write(request);
                    channel.flush();
                });
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
