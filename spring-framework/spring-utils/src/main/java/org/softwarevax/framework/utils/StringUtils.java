package org.softwarevax.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isBlank(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isAnyBlank(CharSequence ... css) {
        if(css == null) {
            return true;
        }
        for(CharSequence cs : css) {
            if(isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static String upperFirstCase(String str){
        char[] chars = str.toCharArray();
        if(chars[0] >= 97 && chars[0] <= 122) {
            // 如果是小写，则转成大写 ，小写：97~122 大写：65~90
            chars[0] -=32;
        }
        return String.valueOf(chars);
    }

    public static String[] split(CharSequence cs, String split) {
        if(isBlank(cs)) {
            return new String[]{};
        }
        if(isBlank(split)) {
            return new String[] {cs.toString()};
        }
        return cs.toString().split(split);
    }

    public static boolean equalsIgnore(CharSequence cs1, CharSequence cs2) {
        if(cs1 == null && cs2 == null) {
            return true;
        }
        if(cs1 == null || cs2 == null) {
            return false;
        }
        if(cs1.length() != cs2.length()) {
            return false;
        }
        if(cs1 == cs2) {
            return true;
        }
        int length = cs1.length();
        for(int i = 0; i < length; i++) {
            if(cs1.charAt(i) == cs2.charAt(i) || ((short) cs1.charAt(i) == (short) cs2.charAt(i) + 32) || ((short) cs1.charAt(i) + 32 == (short) cs2.charAt(i))) {
                continue;
            }
        }
        return true;
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        if(cs1 == null && cs2 == null) {
            return true;
        }
        if(cs1 == null || cs2 == null) {
            return false;
        }
        if(cs1.length() != cs2.length()) {
            return false;
        }
        if(cs1 == cs2) {
            return true;
        }
        int length = cs1.length();
        for(int i = 0; i < length; i++) {
            if(cs1.charAt(i) == cs2.charAt(i)) {
                continue;
            }
        }
        return true;
    }

    public static boolean contains(CharSequence cs1, CharSequence cs2) {
        int len = 0;
        for(int i = 0, size = cs1.length(); i <= size - cs2.length() && len != cs2.length(); i++) {
            len = 0;
            for(int j = 0; j < cs2.length(); j++) {
                if(cs1.charAt(i + j) == cs2.charAt(j)) {
                    len++;
                }
            }
        }
        return len == cs2.length();
    }

    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }
}
