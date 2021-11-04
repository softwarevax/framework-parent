package org.softwarevax.framework.mybatis.proxy;

import java.util.List;

public interface MethodRegistry {

    /**
     * 方法所在类的全限定类名
     * @return
     */
    Class<?> getOwnerClazz();

    /**
     * 方法名称
     * @return
     */
    String getMethodName();

    /**
     * 方法全名（含namespace）
     * @return
     */
    String getMethodFullName();

    /**
     * 方法的参数个数
     * @return
     */
    int getArgsCount();

    /**
     * 方法的参数类型
     * @return
     */
    List<Class<?>> getArgsTypes();

    /**
     * 方法的返回类型
     * @return
     */
    Class<?> getReturnType();
}
