package org.softwarevax.framework.core.annotations;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringApplicationVax {

    String[] packages();

}
