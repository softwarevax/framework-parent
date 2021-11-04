package org.softwarevax.framework.mybatis.proxy;

import java.lang.reflect.Proxy;

public class ProxyUtils {

    public static <T> T getProxy(Class<T> clazz, MapperProxyFactory handler) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler);
    }
}
