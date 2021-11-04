package org.softwarevax.framework.rpc.provider.web.controller;

import org.softwarevax.framework.beans.annotation.AutowiredVax;
import org.softwarevax.framework.beans.annotation.ControllerVax;
import org.softwarevax.framework.mvc.annotations.UrlMapping;
import org.softwarevax.framework.rpc.service.HelloService;

@ControllerVax
@UrlMapping("/hello")
public class HelloController {

    @AutowiredVax
    HelloService helloService;

    @UrlMapping("/test1")
    public String hello() {
        return helloService.hello("maple");
    }
}
