package org.softwarevax.framework.test.mapper;

import org.softwarevax.framework.mybatis.annotation.RepositoryVax;
import org.softwarevax.framework.mybatis.annotation.SelectVax;
import org.softwarevax.framework.test.entity.DeployTask;

import java.util.List;

@RepositoryVax
public interface DeployTaskMapper {

    @SelectVax("select * from deploy_task")
    List<DeployTask> queryList();
}
