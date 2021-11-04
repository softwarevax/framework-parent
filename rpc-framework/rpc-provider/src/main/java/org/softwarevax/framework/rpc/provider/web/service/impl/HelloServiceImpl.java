package org.softwarevax.framework.rpc.provider.web.service.impl;

import org.softwarevax.framework.beans.annotation.ServiceVax;
import org.softwarevax.framework.rpc.service.HelloService;

@ServiceVax
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        return "hello " + msg;
    }
}
