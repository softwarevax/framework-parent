package org.softwarevax.framework.rpc.service.consumer;

import org.softwarevax.framework.beans.annotation.AutowiredVax;
import org.softwarevax.framework.beans.annotation.ControllerVax;
import org.softwarevax.framework.mvc.annotations.UrlMapping;
import org.softwarevax.framework.rpc.service.DeployTask;
import org.softwarevax.framework.rpc.service.DeployTaskService;

import java.util.List;

@ControllerVax
@UrlMapping("/task")
public class DeployTaskController {

    @AutowiredVax(required = false)
    private DeployTaskService deployTaskService;

    @UrlMapping("/findAll")
    public List<DeployTask> findAll() {
        return deployTaskService.findAll();
    }
}
