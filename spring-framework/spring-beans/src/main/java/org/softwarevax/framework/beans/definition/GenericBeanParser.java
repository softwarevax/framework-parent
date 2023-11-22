package org.softwarevax.framework.beans.definition;

import org.softwarevax.framework.beans.factory.BeanDefinition;
import org.softwarevax.framework.beans.factory.GenericBeanDefinition;
import org.softwarevax.framework.utils.BeanUtils;
import org.softwarevax.framework.utils.ClassUtils;

import java.util.Objects;

public class GenericBeanParser implements BeanParser {

    /**
     * 解析完的BeanDefinition
     */
    private BeanDefinition beanDefinition;

    private DependOnDefinitionParser parser = new GenericDependOnDefinitionParser();

    private Class<?> clazz;

    public GenericBeanParser() {
    }

    public GenericBeanParser(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * 将类解析成BeanDefinition
     */
    @Override
    public BeanDefinition parseBeanDefinition(Class<?> clazz) {
        this.clazz = clazz;
        if(Objects.isNull(this.clazz)) {
            return null;
        }
        // 实例化bean，并设置基础属性
        this.beanDefinition = new GenericBeanDefinition(clazz);
        // 类实现的所有接口
        this.beanDefinition.setInterfaces(ClassUtils.getInterfaces(clazz));
        // 设置依赖值(包括构造器、属性、方法)
        this.parser.setClass(clazz);
        this.beanDefinition.setDependsOn(parser.dependOnDefinitions());
        // 设置实例类
        this.beanDefinition.setBeanClass(clazz);
        // 设置父类(继承类)
        this.beanDefinition.setExtendClass(clazz.getSuperclass());
        return this.beanDefinition;
    }

    /**
     * 将类解析成BeanDefinition
     *
     * @param obj
     */
    @Override
    public BeanDefinition parseBeanDefinition(Object obj) {
        return parseBeanDefinition(obj.getClass());
    }

    /**
     * 将BeanDefinition实例化成对象
     */
    @Override
    public Object instantiation(BeanDefinition beanDefinition) {
        Object obj = BeanUtils.newInstance(beanDefinition.getBeanClass().getCanonicalName());
        beanDefinition.setBean(obj);
        return obj;
    }

    /**
     * 给实例化后的对象赋值
     */
    @Override
    public void assignmentDependOn(Object obj) {

    }
}
