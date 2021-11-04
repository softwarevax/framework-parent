package org.softwarevax.framework.beans;


import org.softwarevax.framework.beans.annotation.*;

import java.lang.annotation.Annotation;

/**
 * 全局常量
 */
public class GlobalConstants {

    /**
     * bean的优先级
     */
    public static Class<? extends Annotation>[] BEAN_LEVEL_ANNOTATIONS = new Class[] {PrimaryVax.class};

    /**
     * 所有组件的注解
     */
    public static Class<? extends Annotation>[] COMPONENT_ANNOTATIONS = new Class[] {ControllerVax.class, ServiceVax.class, ComponentVax.class};

    /**
     * 名称属性, 索引和COMPONENT_ANNOTATIONS一一对应
     */
    public static String[] COMPONENT_NAME = new String[] {"name", "name", "name"};

    /**
     * 索引决定优先级
     */
    public static Class<? extends Annotation>[] AUTOWIRED_ANNOTATIONS = new Class[] {AutowiredVax.class};

    /**
     * 名称属性, 索引和AUTOWIRED_ANNOTATIONS一一对应
     */
    public static String[] AUTOWIRED_NAME = new String[] {"name"};
}
