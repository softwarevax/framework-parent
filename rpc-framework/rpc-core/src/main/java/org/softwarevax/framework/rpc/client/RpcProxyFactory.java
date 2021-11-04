package org.softwarevax.framework.rpc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.softwarevax.framework.rpc.BootstrapClient;
import org.softwarevax.framework.rpc.entity.MethodEntity;
import org.softwarevax.framework.rpc.entity.ServiceConfig;
import org.softwarevax.framework.rpc.protocol.ClientHandler;
import org.softwarevax.framework.utils.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class RpcProxyFactory implements InvocationHandler {

    private ServiceConfig config;

    public RpcProxyFactory(ServiceConfig config) {
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        BootstrapClient client = new BootstrapClient(config.getHostName(), config.getPort());
        String request = HttpClient.postBuilder(config.getHostName() + ":" + config.getPort(), Constants.RPC_INVOKE, JSON.toJSONString(config));
        Channel channel = client.getChannel();
        MethodEntity methodEntity = config.getServices().get(0).getMethods().get(0);
        methodEntity.setResult(null);
        channel.write(request);
        Type genericReturnType = method.getGenericReturnType();
        Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        Class<?> returnClazz = ClassUtils.loadClass(actualTypeArguments[0].getTypeName());
        channel.pipeline().addLast(new ClientHandler() {
            public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
                context.fireChannelRead(msg);
                if(!((String) msg).contains(Constants.RPC_INVOKE)) {
                    return;
                }
                String body = HttpClient.getFormBody((String) msg);
                if(body.startsWith("[{")) {
                    methodEntity.setResult(JSONArray.parseArray(body, returnClazz));
                } else {
                    methodEntity.setResult(JSONObject.parseObject(body, returnClazz));
                }
            }
        });
        channel.flush();
        long timeOut = 0;
        while (methodEntity.getResult() == null && timeOut < 5000) {
            Thread.sleep(10);
            timeOut += 20;
        }
        return methodEntity.getResult();
    }
}
