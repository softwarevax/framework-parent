package org.softwarevax.framework.beans.factory;

import org.softwarevax.framework.beans.definition.DependOnDefinition;
import org.softwarevax.framework.beans.enums.InstanceType;

import java.util.List;

/**
 * bean
 */
public interface BeanDefinition {

    /**
     * 是否是最主要的
     * @return 返回标识
     */
    boolean isPrimary();

    /**
     * 容器中的实例名
     * @param beanName
     * @return
     */
    void setBeanName(String beanName);

    /**
     * 获取bean的名称
     * @return 名称
     */
    String getBeanName();

    /**
     * 获取实例
     * @return 返回实例
     */
    Object getBean();

    /**
     * @param obj 实例对象
     */
    void setBean(Object obj);

    /**
     * @param dependsOn 实例对象依赖的对象
     */
    void setDependsOn(List<DependOnDefinition> dependsOn);

    /**
     * @return 返回对象依赖的对象
     */
    List<DependOnDefinition> getDependsOn();

    /**
     * @return 返回实例的类
     */
    Class<?> getBeanClass();

    /**
     * @param beanClass 实例的类
     */
    void setBeanClass(Class<?> beanClass);

    /**
     * @return 返回实例类的接口
     */
    List<Class<?>> getInterfaces();

    /**
     * @param interfaces 设置实例类的接口
     */
    void setInterfaces(List<Class<?>> interfaces);

    /**
     * @return 返回实例类的继承的类
     */
    Class<?> getExtendClass();

    /**
     * @param extendClass 设置实例类的继承的类
     */
    void setExtendClass(Class<?> extendClass);

    /**
     * @return 返回实例注入的方式
     */
    InstanceType getInjectType();

    /**
     * @param type 设置实例注入的方式
     */
    void setInjectType(InstanceType type);

    /**
     * 是否已刷新
     */
    boolean isRefreshed();
}
