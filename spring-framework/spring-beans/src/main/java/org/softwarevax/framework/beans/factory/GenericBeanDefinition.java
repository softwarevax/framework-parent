package org.softwarevax.framework.beans.factory;

import org.softwarevax.framework.beans.CommonsUtils;
import org.softwarevax.framework.beans.GlobalConstants;
import org.softwarevax.framework.beans.definition.DependOnDefinition;
import org.softwarevax.framework.utils.AnnotationUtils;
import org.softwarevax.framework.utils.Assert;
import org.softwarevax.framework.utils.BeanUtils;

import java.lang.annotation.Annotation;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {

    /**
     * 因为哪个注解被注入到spring容器中的， 如果被多个标记，则记录生效的那个
     */
    private Class<? extends Annotation> annotatedClazz;

    /**
     * 实例化bean的全限定类名
     */
    private Class<?> beanClass;

    /**
     * 实例bean类名
     */
    private String beanName;

    /**
     *依赖的实例key
     */
    private List<DependOnDefinition> dependsOn;

    /**
     * spring bean
     */
    private Object bean;

    /**
     * 是否是重要的实例
     */
    private boolean isPrimary;

    public GenericBeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        this.annotatedClazz = CommonsUtils.getComponentAnnotation(beanClass);
        this.beanName = CommonsUtils.parseBeanName(beanClass);
        this.isPrimary = AnnotationUtils.containsAnyAnnotation(beanClass, GlobalConstants.BEAN_LEVEL_ANNOTATIONS);
        this.bean = BeanUtils.newInstance(beanName);
    }

    public GenericBeanDefinition(Object bean) {
        Assert.notNull(bean, "bean不可同时为空");
        this.beanClass = bean.getClass();
        this.annotatedClazz = CommonsUtils.getComponentAnnotation(beanClass);
        this.beanName = CommonsUtils.parseBeanName(beanClass);
        this.isPrimary = AnnotationUtils.containsAnyAnnotation(beanClass, GlobalConstants.BEAN_LEVEL_ANNOTATIONS);
        this.bean = bean;
    }

    public void setDependsOn(List<DependOnDefinition> dependsOn) {
        this.dependsOn = dependsOn;
    }

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
}
