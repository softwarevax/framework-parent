package org.softwarevax.framework.utils;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

public class ObjectUtils {

    public static <T> T get(Object obj, String properties) {
        Assert.notNull(obj, "obj cannot be null");
        try {
            Class<?> clazz = obj.getClass();
            Field declaredField = clazz.getDeclaredField(properties);
            declaredField.setAccessible(true);
            return (T)declaredField.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void set(Object obj, String fieldName, Object val) {
        Assert.notNull(obj, "obj cannot be null");
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, val);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Object set(Object obj, Class<?> beanClazz, AnnotatedElement element, Object val) {
        Assert.notNull(obj, "obj cannot be null");
        Assert.notNull(obj, "element cannot be null");
        try {
            if(element instanceof Field) {
                Field field = (Field) element;
                set(obj, field.getName(), val);
            }
            if(element instanceof Parameter) {
                Parameter parameter = (Parameter) element;
                Method method = (Method)parameter.getDeclaringExecutable();
                method.invoke(obj, val);
            }
            if(element instanceof Method) {
                Method method = (Method) element;
                Parameter[] parameters = method.getParameters();
                Object[] args = new Object[parameters.length];
                for(int i = 0, len = parameters.length; i < len; i++) {
                    Parameter parameter = parameters[i];
                    Class<?> parameterType = parameter.getType();
                    if(parameterType.isAssignableFrom(beanClazz)) {
                        args[i] = val;
                    }
                }
                method.invoke(obj, args);
            }
            if(element instanceof Constructor) {
                Constructor constructor = (Constructor) element;
                Parameter[] parameters = constructor.getParameters();
                int parameterCount = constructor.getParameterCount();
                Object[] args = new Object[parameterCount];
                for(int i = 0, len = parameters.length; i < len; i++) {
                    Parameter parameter = parameters[i];
                    Class<?> parameterType = parameter.getType();
                    if(!parameterType.isAssignableFrom(beanClazz)) {
                        continue;
                    }
                    args[i] = val;
                }
                return constructor.newInstance(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Object invoke(Object obj, Method method, Object ... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Object obj, String methodName, Object ... argTypes) {
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取第一个不为null的值, 若都为null，则返回null
     * @param val
     * @return
     */
    public static Object firstNotNull(Object ... val) {
        for (int i = 0; i < val.length; i++) {
            if(val[i] != null) {
                return val[i];
            }
        }
        return null;
    }

    public static boolean isSimpleType(Class<?> clazz) {
        if(clazz == boolean.class || clazz == Boolean.class ||
           clazz == byte.class || clazz == Byte.class ||
           clazz == char.class || clazz == Character.class ||
           clazz == short.class || clazz == Short.class ||
           clazz == int.class || clazz == Integer.class ||
           clazz == float.class || clazz == Float.class ||
           clazz == double.class || clazz == Double.class ||
           clazz == long.class || clazz == Long.class ||
           clazz == String.class || clazz.isAssignableFrom(Map.class) || clazz.isAssignableFrom(Collection.class))
            return true;
        return false;
    }
}
