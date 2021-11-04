package org.softwarevax.framework.beans.definition;

import org.softwarevax.framework.beans.factory.InjectMode;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.AnnotatedElement;

/**
 * 注入的属性
 */
public interface DependOnDefinition {

    /**
     * 当前元素的归属类
     * @return
     */
    Class<?> getOwnerClass();

    /**
     * 当前元素的归属在容器中的名称
     * @return
     */
    String getOwnerBeanName();

    /**
     * 获取自动注入的方式
     * 仅支持：ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER
     * @return
     */
    ElementType getAutowiredType();

    /**
     * 获取待注入的bean名称, 如果是方法或者参数类型，该值为空
     * @return 返回名称
     */
    default String getBeanName() {return "";};

    /**
     * 获取使自动注入生效的注解
     */
    Class<? extends Annotation> getAnnotation();

    /**
     * 获取被注解标记的元素
     * @return
     */
    AnnotatedElement getAnnotatedElement();

    /**
     * 获取bean注入的方式
     * @return
     */
    InjectMode getInjectMode();

    /**
     * 获取依赖值
     * @return
     */
    Object getDependOnBean();

    /**
     * 设置依赖
     * @param val
     */
    void setDependOnBean(Object val);

    /**
     * 是注入生效的注解
     * @return
     */
    Class<? extends Annotation> getInjectAnnotation();

    /**
     * 是否是必须注入的
     * @return
     */
    default boolean isRequired() {
        return true;
    }
}
