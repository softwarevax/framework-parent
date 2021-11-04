package org.softwarevax.framework.tomcat.utils;

import java.util.Collection;
import java.util.Map;

public class Assert {

    /**
     * 断言布尔类型
     * @param expression
     * @param errorMsg
     * @return
     */
    public static boolean isTrue(boolean expression, String errorMsg) {
        if(!expression) {
            throw new IllegalArgumentException(errorMsg);
        }
        return true;
    }

    /**
     * 断言布尔类型
     * @param str
     * @param errorMsg
     * @return
     */
    public static boolean notBlank(String str, String errorMsg) {
        if(str == null || str.length() == 0) {
            throw new IllegalArgumentException(errorMsg);
        }
        return true;
    }

    /**
     * 断言非空
     * @param expression
     * @param errorMsg
     * @return
     */
    public static boolean notNull(Object expression, String errorMsg) {
        if(expression == null) {
            throw new IllegalArgumentException(errorMsg);
        }
        return true;
    }

    /**
     * 断言集合非空
     * @param expression
     * @param errorMsg
     * @return
     */
    public static boolean notEmpty(Collection<?> expression, String errorMsg) {
        if(expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException(errorMsg);
        }
        return true;
    }

    /**
     * 断言键值对非空
     * @param expression
     * @param errorMsg
     * @return
     */
    public static boolean notEmpty(Map expression, String errorMsg) {
        if(expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException(errorMsg);
        }
        return true;
    }
}
