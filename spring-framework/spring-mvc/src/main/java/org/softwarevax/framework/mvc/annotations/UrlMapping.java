package org.softwarevax.framework.mvc.annotations;


import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface UrlMapping {

    String value() default "";
}
