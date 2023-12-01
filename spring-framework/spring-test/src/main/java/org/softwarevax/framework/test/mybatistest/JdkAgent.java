package org.softwarevax.framework.test.mybatistest;

import org.softwarevax.framework.mybatis.annotation.SelectVax;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkAgent implements InvocationHandler {

    private Object obj;

    public JdkAgent(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String sql = method.getAnnotation(SelectVax.class).value();
        if(obj instanceof JdbcManager) {
            JdbcManager template = (JdbcManager) obj;
            return template.statement(sql);
        }
        return null;
    }
}
