package org.softwarevax.framework.beans.definition;

import org.softwarevax.framework.beans.factory.BeanDefinition;

public interface BeanParser {

    /**
     * 将类解析成BeanDefinition
     */
    BeanDefinition parseBeanDefinition(Class<?> clazz);

    /**
     * 将类解析成BeanDefinition
     */
    BeanDefinition parseBeanDefinition(Object obj);

    /**
     * 将BeanDefinition实例化成对象
     */
    Object instantiation(BeanDefinition beanDefinition);

    /**
     * 给实例化后的对象赋值
     */
    void assignmentDependOn(Object obj);

}
