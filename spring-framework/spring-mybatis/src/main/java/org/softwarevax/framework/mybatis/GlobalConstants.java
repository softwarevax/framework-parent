package org.softwarevax.framework.mybatis;


import org.softwarevax.framework.mybatis.annotation.Mapper;
import org.softwarevax.framework.mybatis.annotation.RepositoryVax;

import java.lang.annotation.Annotation;

public class GlobalConstants {
    /**
     * 所有组件的注解
     */
    public static Class<? extends Annotation>[] MAPPER_ANNOTATIONS = new Class[] {RepositoryVax.class, Mapper.class};
}
