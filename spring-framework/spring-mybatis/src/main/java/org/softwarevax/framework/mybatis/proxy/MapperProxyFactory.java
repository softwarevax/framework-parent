package org.softwarevax.framework.mybatis.proxy;

import org.softwarevax.framework.mybatis.annotation.SelectVax;
import org.softwarevax.framework.mybatis.jdbc.JdbcTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxyFactory implements InvocationHandler {

    private Object obj;

    public MapperProxyFactory(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 方法全名： method.getDeclaringClass().getCanonicalName() + "." + method.getName()
        String sql = method.getAnnotation(SelectVax.class).value();
        if(obj instanceof JdbcTemplate) {
            JdbcTemplate template = (JdbcTemplate) obj;
            return template.statement(sql);
        }
        return null;
    }
}
