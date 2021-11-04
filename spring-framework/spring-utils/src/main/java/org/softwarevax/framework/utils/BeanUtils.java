package org.softwarevax.framework.utils;

import java.lang.reflect.Constructor;

public class BeanUtils {

    /**
     * 根据类名创建实例
     * @param className 全限定类名
     * @param <T> 返回结果的类型
     * @return 返回结果
     */
    public static <T> T newInstance(String className) {
        if(className == null || "".equals(className)) {
            return null;
        }
        try {
            Class<T> clazz = (Class<T>) Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        }
        try {
            Class<T> clazz = (Class<T>) Class.forName(className);
            Constructor[] constructors = clazz.getConstructors();
            for(Constructor constructor : constructors) {
                Object obj = null;
                try {
                    int parameterCount = constructor.getParameterCount();
                    Object[] args = new Object[parameterCount];
                    obj = constructor.newInstance(args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                if(obj != null) {
                    return (T)obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据类创建实例
     * @param clazz 全限定类名
     * @param <T> 返回结果的类型
     * @return 返回结果
     */
    public static <T> T newInstance(Class<T> clazz) {
        if(clazz == null) {
            return null;
        }
        return newInstance(clazz.getName());
    }
}
