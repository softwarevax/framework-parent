package org.softwarevax.framework.test.mybatistest;

import org.softwarevax.framework.mybatis.annotation.SelectVax;

import java.util.List;

public interface DeployTaskMapper {

    @SelectVax("select * from deploy_task")
    List<Object> queryList();
}
