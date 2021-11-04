package org.softwarevax.framework.rpc.utils;

import org.softwarevax.framework.rpc.client.RpcProxyFactory;

import java.lang.reflect.Proxy;

public class ProxyUtils {

    public static <T> T getProxy(Class<T> clazz, RpcProxyFactory handler) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
    }
}
