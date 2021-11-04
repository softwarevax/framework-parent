package org.softwarevax.framework.rpc.provider.web.mapper;

import org.softwarevax.framework.mybatis.annotation.RepositoryVax;
import org.softwarevax.framework.mybatis.annotation.SelectVax;
import org.softwarevax.framework.rpc.service.DeployTask;

import java.util.List;

@RepositoryVax
public interface DeployTaskMapper {

    @SelectVax("select * from qrtz_deploy_task")
    List<DeployTask> queryList();
}
