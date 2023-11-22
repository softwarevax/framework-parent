package org.softwarevax.framework.test.service.impl;

import org.softwarevax.framework.beans.annotation.AutowiredVax;
import org.softwarevax.framework.beans.annotation.ServiceVax;
import org.softwarevax.framework.test.entity.DeployTask;
import org.softwarevax.framework.test.mapper.DeployTaskMapper;
import org.softwarevax.framework.test.service.DeployTaskService;

import java.util.List;

@ServiceVax(name = "deployTaskService")
public class DeployTaskServiceImpl implements DeployTaskService {

    @AutowiredVax
    private DeployTaskMapper taskMapper;

    @Override
    public List<DeployTask> findAll() {
        return taskMapper.queryList();
    }

    @Override
    public String hello(String msg) {
        return "hello " + msg;
    }
}
