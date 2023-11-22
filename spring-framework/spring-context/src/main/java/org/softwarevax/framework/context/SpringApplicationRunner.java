package org.softwarevax.framework.context;

import org.softwarevax.framework.core.annotations.SpringApplicationVax;
import org.softwarevax.framework.utils.AnnotationUtils;
import org.softwarevax.framework.utils.Assert;

public class SpringApplicationRunner {

    public static ApplicationContext run(Class<?> mainClass) {
        Assert.notNull(mainClass, "mainClass cannot be null");
        SpringApplicationVax springApplication = AnnotationUtils.getAnnotation(mainClass, SpringApplicationVax.class);
        ApplicationContext context = new AnnotationApplicationContext(springApplication.packages());
        return context;
    }
}
