package org.softwarevax.framework.beans.factory;

import org.softwarevax.framework.beans.CommonsUtils;
import org.softwarevax.framework.beans.GlobalConstants;
import org.softwarevax.framework.beans.definition.DependOnDefinition;
import org.softwarevax.framework.beans.enums.InstanceType;
import org.softwarevax.framework.utils.AnnotationUtils;

import java.util.List;
import java.util.Objects;

public class GenericBeanDefinition implements BeanDefinition {

    /**
     * 是否是重要的实例
     */
    private boolean isPrimary;

    /**
     * 实例bean类名
     */
    private String beanName;

    /**
     * spring bean
     */
    private Object bean;

    /**
     *依赖的实例key
     */
    private List<DependOnDefinition> dependsOn;

    /**
     * 实例化bean的全限定类名
     */
    private Class<?> beanClass;

    /**
     * 因为哪个注解被注入到spring容器中的， 如果被多个标记，则记录生效的那个
     */
    private Class<?> annotatedClazz;

    /**
     * 类的继承类
     */
    private Class<?> extendClass;

    /**
     * 类实现的接口
     */
    private List<Class<?>> interfaces;

    /**
     * 注入方式
     */
    private InstanceType injectType;

    public GenericBeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.annotatedClazz = CommonsUtils.getComponentAnnotation(beanClass);
        this.beanName = CommonsUtils.parseBeanName(beanClass);
        this.isPrimary = AnnotationUtils.containsAnyAnnotation(beanClass, GlobalConstants.BEAN_LEVEL_ANNOTATIONS);
    }

    @Override
    public void setDependsOn(List<DependOnDefinition> dependsOn) {
        this.dependsOn = dependsOn;
    }

    @Override
    public List<DependOnDefinition> getDependsOn() {
        return this.dependsOn;
    }

    @Override
    public boolean isPrimary() {
        return this.isPrimary;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public Object getBean() {
        return this.bean;
    }

    @Override
    public void setBean(Object bean) {
        this.bean = bean;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public List<Class<?>> getInterfaces() {
        return this.interfaces;
    }

    @Override
    public void setInterfaces(List<Class<?>> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public Class<?> getExtendClass() {
        return this.extendClass;
    }

    @Override
    public void setExtendClass(Class<?> extendClass) {
        this.extendClass = extendClass;
    }

    @Override
    public InstanceType getInjectType() {
        return this.injectType;
    }

    @Override
    public void setInjectType(InstanceType type) {
        this.injectType = type;
    }

    /**
     * 是否已刷新
     */
    @Override
    public boolean isRefreshed() {
        for (DependOnDefinition bean : dependsOn) {
            if(Objects.isNull(bean.getDependOnBean())) {
                return false;
            }
        }
        return true;
    }
}
