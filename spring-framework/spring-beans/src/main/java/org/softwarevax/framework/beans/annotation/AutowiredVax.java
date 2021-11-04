package org.softwarevax.framework.beans.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutowiredVax {

    boolean required() default true;

    String name() default "";
}
