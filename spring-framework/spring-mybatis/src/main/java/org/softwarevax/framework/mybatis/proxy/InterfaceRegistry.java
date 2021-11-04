package org.softwarevax.framework.mybatis.proxy;

public interface InterfaceRegistry {

    /**
     * 方法全名（含namespace）
     * @return
     */
    String getMethodFullName();

    /**
     * 方法名称
     * @return
     */
    String getMethodName();

    /**
     * 获取返回类型
     * @return
     */
    Class<?> getReturnType();

    /**
     * 查询的sql语句
     * @return
     */
    String getSql();
}
