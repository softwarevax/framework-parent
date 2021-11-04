package org.softwarevax.framework.rpc.utils;

public class Assert {

    public static void isTrue(boolean flag, String errorMsg) {
        if(flag) {
            return;
        }
        throw new IllegalArgumentException(errorMsg);
    }
}
