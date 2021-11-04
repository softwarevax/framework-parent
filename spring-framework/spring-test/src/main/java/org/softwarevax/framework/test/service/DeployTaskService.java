package org.softwarevax.framework.test.service;

import org.softwarevax.framework.test.entity.DeployTask;

import java.util.List;

public interface DeployTaskService {

    List<DeployTask> findAll();
}
