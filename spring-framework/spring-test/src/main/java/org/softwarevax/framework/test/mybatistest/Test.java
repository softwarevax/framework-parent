package org.softwarevax.framework.test.mybatistest;

import java.lang.reflect.Proxy;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        JdbcManager manager = new JdbcManager();
        JdkAgent agent = new JdkAgent(manager);
        DeployTaskMapper mapper = (DeployTaskMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {DeployTaskMapper.class}, agent);
        List<Object> objects = mapper.queryList();
        System.out.println(objects);
    }
}
