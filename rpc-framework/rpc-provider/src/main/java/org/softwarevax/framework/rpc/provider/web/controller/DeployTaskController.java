package org.softwarevax.framework.rpc.provider.web.controller;

import org.softwarevax.framework.beans.annotation.AutowiredVax;
import org.softwarevax.framework.beans.annotation.ControllerVax;
import org.softwarevax.framework.mvc.annotations.UrlMapping;
import org.softwarevax.framework.rpc.service.DeployTask;
import org.softwarevax.framework.rpc.service.DeployTaskService;

import java.util.List;

@ControllerVax
@UrlMapping("/task")
public class DeployTaskController {

    @AutowiredVax
    DeployTaskService deployTaskService;

    @UrlMapping("/findAll")
    public List<DeployTask> findAll() {
        return deployTaskService.findAll();
    }

    @UrlMapping("/findAll1")
    public List<DeployTask> findAll1(String name) {
        return deployTaskService.findAll();
    }
}
