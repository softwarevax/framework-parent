package org.softwarevax.framework.test.web;

import org.softwarevax.framework.beans.annotation.AutowiredVax;
import org.softwarevax.framework.beans.annotation.ControllerVax;
import org.softwarevax.framework.mvc.annotations.UrlMapping;
import org.softwarevax.framework.test.service.DeployTaskService;

@ControllerVax
@UrlMapping("/task")
public class DeployTaskController {

    @AutowiredVax(name = "deployTaskService")
    private DeployTaskService helloService;

    @UrlMapping("/findAll")
    public Object hello() {
        return helloService.findAll();
    }
}
