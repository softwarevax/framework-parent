package org.softwarevax.framework.beans.definition;

import org.softwarevax.framework.beans.CommonsUtils;
import org.softwarevax.framework.beans.GlobalConstants;
import org.softwarevax.framework.beans.factory.InjectMode;
import org.softwarevax.framework.utils.AnnotationUtils;
import org.softwarevax.framework.utils.ClassUtils;
import org.softwarevax.framework.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.*;

public class GenericDependOnDefinition implements DependOnDefinition {

    /**
     * 方法、属性，构造器，参数
     */
    protected AnnotatedElement annotatedElement;

    /**
     * 使自动注入生效的注解
     */
    protected Class<? extends Annotation> enableInjectAnnotation;

    /**
     * 待注入bean的名称
     */
    protected String beanName;

    /**
     * 当前元素是属于哪个类
     */
    protected Class<?> ownerClazz;

    /**
     * 所在类的beanName
     */
    protected String ownerBeanName;

    /**
     * 注入的方式
     */
    private InjectMode injectMode;

    /**
     * 依赖实例
     */
    private Object dependOnBean;

    /**
     * 是否是必须注入的
     */
    private boolean isRequired;

    public GenericDependOnDefinition(Class<?> ownerClazz, AnnotatedElement annotatedElement) {
        this.ownerClazz = ownerClazz;
        this.annotatedElement = annotatedElement;
        int i = 0;
        for(Class<? extends Annotation> clazz : GlobalConstants.AUTOWIRED_ANNOTATIONS) {
            if(this.annotatedElement.isAnnotationPresent(clazz)) {
                this.enableInjectAnnotation = clazz;
                this.isRequired = AnnotationUtils.get(annotatedElement, clazz, "required");
                this.beanName = AnnotationUtils.get(annotatedElement, clazz, GlobalConstants.AUTOWIRED_NAME[i]);
                this.injectMode = StringUtils.isBlank(beanName) ? InjectMode.TYPE : InjectMode.NAME;
                this.beanName = StringUtils.isBlank(beanName) ? ClassUtils.getType(annotatedElement).getCanonicalName() : beanName;
                this.ownerBeanName = CommonsUtils.parseBeanName(ownerClazz);
                break;
            }
            i++;
        }
    }

    @Override
    public ElementType getAutowiredType() {
        if(annotatedElement instanceof Method) {
            return ElementType.METHOD;
        } else if(annotatedElement instanceof Field) {
            return ElementType.FIELD;
        } else if(annotatedElement instanceof Constructor) {
            return ElementType.CONSTRUCTOR;
        } else if(annotatedElement instanceof Parameter) {
            return ElementType.PARAMETER;
        }
        throw new RuntimeException("不支持的注解位置");
    }

    public Class<? extends Annotation> getAnnotation() {
        return this.enableInjectAnnotation;
    }

    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public Class<?> getOwnerClass() {
        return this.ownerClazz;
    }

    @Override
    public String getOwnerBeanName() {
        return this.ownerBeanName;
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return this.annotatedElement;
    }

    @Override
    public InjectMode getInjectMode() {
        return this.injectMode;
    }

    @Override
    public Object getDependOnBean() {
        return this.dependOnBean;
    }

    @Override
    public void setDependOnBean(Object val) {
        this.dependOnBean = val;
    }

    @Override
    public Class<? extends Annotation> getInjectAnnotation() {
        return this.enableInjectAnnotation;
    }

    @Override
    public boolean isRequired() {
        return this.isRequired;
    }
}
