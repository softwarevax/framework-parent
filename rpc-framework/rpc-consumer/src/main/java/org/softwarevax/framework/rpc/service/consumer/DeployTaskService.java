package org.softwarevax.framework.rpc.service.consumer;


import org.softwarevax.framework.rpc.service.DeployTask;

import java.util.List;

public interface DeployTaskService {

    List<DeployTask> findAll();
}
