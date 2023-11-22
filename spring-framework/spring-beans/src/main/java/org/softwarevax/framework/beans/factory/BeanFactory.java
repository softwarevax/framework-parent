package org.softwarevax.framework.beans.factory;

import java.util.List;

/**
 * 实例工厂
 */
public interface BeanFactory {

    /**
     * 根据bean的名称获取实例
     * @param beanName 实例名称
     * @return 实例
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 根据bean的class获取实例
     * @param clazz 实例clazz
     * @return 实例
     * @throws BeansException
     */
    <T> List<T> getBeans(Class<T> clazz) throws BeansException;

    <T> T getBean(Class<T> clazz) throws BeansException;

    /**
     * 注册bean
     * @param beanName 容器中的名字 默认为全限定类名
     * @param clazz 全限定类名
     * @param <T> 约束
     * @return 实例
     * @throws BeansException
     */
    <T> T registry(String beanName, Class<T> clazz) throws BeansException;

    /**
     * 注册bean
     * @param clazz 全限定类名
     * @param <T> 约束
     * @return 实例(容器中的名字 默认为全限定类名)
     * @throws BeansException
     */
    <T> T registry(Class<T> clazz) throws BeansException;

    /**
     * 将实例放入容器中
     * @param bean
     * @param <T>
     * @return
     */
    <T> T registry(T bean);

    /**
     * 将实例放入容器中
     * @param bean
     * @param <T>
     * @return
     */
    <T> T registry(String beanName, T bean);

    /**
     * 获取bean的定义
     * @param clazz
     * @return
     */
    BeanDefinition getDefinition(String clazz);
}
