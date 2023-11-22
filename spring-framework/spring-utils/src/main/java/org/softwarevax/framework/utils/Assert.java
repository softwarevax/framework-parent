package org.softwarevax.framework.utils;

import java.util.List;

public class Assert {

    /**
     * 判断对象是否为空
     * @param obj 实例
     * @param msg 不满足条件时的错误信息
     */
    public static void notNull(Object obj, String msg) {
        if(obj == null) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 字符串是否为null，且内容为空
     * @param cs 字符串
     * @param msg 不满足条件时的错误信息
     */
    public static void isBlank(CharSequence cs, String msg) {
        if(!StringUtils.isBlank(cs)) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 字符串是否不为null，且内容不为空
     * @param cs 字符串
     * @param msg 不满足条件时的错误信息
     */
    public static void isNotBlank(CharSequence cs, String msg) {
        if(StringUtils.isBlank(cs)) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 对象数组是否不为null，且长度不为0
     * @param objs 对象数组
     * @param msg 不满足条件时的错误信息
     */
    public static void notEmpty(Object[] objs, String msg) {
        if(objs == null || objs.length == 0) {
            throw new RuntimeException(msg);
        }
    }

    public static void notEmpty(List<?> objs, String msg) {
        if(objs == null || objs.size() == 0) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 表达式是否为真
     * @param expression
     * @param msg
     */
    public static void isTrue(boolean expression, String msg) {
        if(!expression) {
            throw new RuntimeException(msg);
        }
    }
}
