package org.softwarevax.framework.rpc.provider.web.service.impl;

import org.softwarevax.framework.beans.annotation.AutowiredVax;
import org.softwarevax.framework.beans.annotation.ServiceVax;
import org.softwarevax.framework.rpc.provider.web.mapper.DeployTaskMapper;
import org.softwarevax.framework.rpc.service.DeployTask;
import org.softwarevax.framework.rpc.service.DeployTaskService;

import java.util.List;

@ServiceVax
public class DeployTaskServiceImpl implements DeployTaskService {

    @AutowiredVax
    DeployTaskMapper deployTaskMapper;

    @Override
    public List<DeployTask> findAll() {
        return deployTaskMapper.queryList();
    }
}
