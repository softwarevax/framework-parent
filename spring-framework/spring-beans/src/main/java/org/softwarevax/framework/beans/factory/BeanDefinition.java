package org.softwarevax.framework.beans.factory;

import org.softwarevax.framework.beans.definition.DependOnDefinition;

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
     * 容器中的实名名
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

    void setBean(Object obj);

    void setDependsOn(List<DependOnDefinition> dependsOn);

    List<DependOnDefinition> getDependsOn();

    Class<?> getBeanClass();

    void setBeanClass(Class<?> beanClass);
}
