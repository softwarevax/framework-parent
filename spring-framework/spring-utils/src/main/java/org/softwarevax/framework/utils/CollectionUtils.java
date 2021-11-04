package org.softwarevax.framework.utils;

import java.util.Collection;

public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static int length(Collection collection) {
        if(isEmpty(collection)) {
            return 0;
        }
        return collection.size();
    }
}
