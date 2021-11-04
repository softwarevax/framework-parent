package org.softwarevax.framework.utils;

public class PathUtils {

    public static String merge(String ... path) {
        if(path.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer("/");
        for(int i = 0, size = path.length; i< size; i++) {
            sb.append(path[i]).append("/");
        }
        String paths = sb.toString().replace("///", "/").replace("//", "/");
        return paths.substring(0, paths.length() -1);
    }
}
