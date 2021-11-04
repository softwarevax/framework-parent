package org.softwarevax.framework.rpc;

import org.softwarevax.framework.rpc.entity.RpcEntity;

import java.util.List;

public interface RpcInvokeHandler {

    /**
     * 远程调用，返回数组
     * @param rpcEntity 请求参数
     * @return
     */
    List<Object> invoke(RpcEntity rpcEntity);
}
