package org.softwarevax.framework.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解工具类
 */
public class AnnotationUtils {

    /**
     * 获取类、方法、属性注解的属性值
     * @param annotated 实现了AnnotatedElement类、方法、属性、构造器、属性等
     * @param annoClazz 注解
     * @param methodName 注解的方法
     * @param <T> 返回的结果类型约束
     * @return 返回的结果
     */
    public static <T> T get(AnnotatedElement annotated, Class<? extends Annotation> annoClazz, String methodName) {
        try {
            Assert.notNull(annotated, "please pass into the class,field or method");
            Assert.notNull(annoClazz, "Annotation cannot be null");
            if(StringUtils.isBlank(methodName)) {
                methodName = "value";
            }
            Method method = annoClazz.getDeclaredMethod(methodName);
            Annotation anno = annotated.getAnnotation(annoClazz);
            Assert.notNull(anno, annotated + " is not marked by " + annoClazz);
            return (T)method.invoke(anno);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("method [" + annoClazz.getCanonicalName() + "." + methodName + "()] does not exist");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类、方法、属性注解的value值
     * @param annotated 实现了AnnotatedElement类、方法、属性、构造器、属性等
     * @param annoClazz 注解
     * @param <T> 返回的结果类型约束
     * @return 返回的结果
     */
    public static <T> T get(AnnotatedElement annotated, Class<? extends Annotation> annoClazz) {
        return get(annotated, annoClazz, null);
    }

    /**
     * 获取所有注解
     * @param annotated 实现了annotated接口的类、属性、方法等
     * @return 类的所有注解
     */
    public static Annotation[] getAnnotation(AnnotatedElement annotated) {
        Assert.notNull(annotated, "AnnotatedElement cannot be null");
        return annotated.getAnnotations();
    }

    /**
     * 获取类的指定注解
     * @param annotated 全限定类
     * @return 类的注解
     */
    public static <T extends Annotation> T getAnnotation(AnnotatedElement annotated, Class<T> annotation) {
        Assert.notNull(annotated, "class cannot be null");
        Assert.notNull(annotation, "annotation cannot be null");
        return annotated.getAnnotation(annotation);
    }

    /**
     * 是否含有某个注解
     * @param annotated
     * @param annotations
     * @return
     */
    public static boolean containsAnyAnnotation(AnnotatedElement annotated, Class<?> ... annotations) {
        Assert.notNull(annotated, "AnnotatedElement cannot be null");
        Assert.notEmpty(annotations, "annotation cannot be null");
        for(Class annotation : annotations) {
            if(annotated.getAnnotation(annotation) != null) {
                return true;
            }
        }
        return false;
    }
}
