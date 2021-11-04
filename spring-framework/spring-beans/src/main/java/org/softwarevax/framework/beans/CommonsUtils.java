package org.softwarevax.framework.beans;

import org.softwarevax.framework.utils.AnnotationUtils;
import org.softwarevax.framework.utils.StringUtils;

import java.lang.annotation.Annotation;

public class CommonsUtils {

    /**
     * 获取被组件标记的注解，取生效的那个
     * @param beanClass
     * @return
     */
    public static Class<? extends Annotation> getComponentAnnotation(Class<?> beanClass) {
        int len = GlobalConstants.COMPONENT_ANNOTATIONS.length;
        for(int i = len - 1; i >= 0; i-- ) {
            Class<? extends Annotation> annoClazz = GlobalConstants.COMPONENT_ANNOTATIONS[i];
            if(beanClass.isAnnotationPresent(annoClazz)) {
                return annoClazz;
            }
        }
        return null;
    }

    /**
     * 从注解中获取beanName
     * @param beanClass
     * @return
     */
    private static String parseBeanNameByAnnotated(Class<?> beanClass) {
        String beanName = null;
        Class<? extends Annotation> annotated = getComponentAnnotation(beanClass);
        int len = GlobalConstants.COMPONENT_ANNOTATIONS.length;
        for(int i = len - 1; i >= 0; i-- ) {
            if(GlobalConstants.COMPONENT_ANNOTATIONS[i] == annotated) { //注： 被不同classloader加载的类会不相等
                beanName = AnnotationUtils.get(beanClass, annotated, GlobalConstants.COMPONENT_NAME[i]);
            }
        }
        return StringUtils.isBlank(beanName) ? beanClass.getCanonicalName() : beanName;
    }

    /**
     * 从多个注解中获取bean的名称
     * @param beanClass
     * @return
     */
    public static String parseBeanName(Class<?> beanClass) {
        return CommonsUtils.parseBeanNameByAnnotated(beanClass);
    }
}
