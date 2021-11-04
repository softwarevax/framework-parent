package org.softwarevax.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertyUtils {

    public static Properties getProperties(InputStream is) throws IOException {
        Properties prop = new Properties();
        prop.load(is);
        return prop;
    }

    /**
     * 获取指定路径文件的配置属性
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String filePath) throws IOException {
        return getProperties(new FileInputStream(filePath));
    }

    public static Properties parse(Map<String, String> map) {
        Properties prop = new Properties();
        if(map == null || map.isEmpty()) {
            return prop;
        }
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            prop.setProperty(next.getKey(), next.getValue());
        }
        return prop;
    }

    /**
     * 获取classpath配置属性
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Properties getClassPathProperties(String fileName) throws IOException {
        return getProperties(PropertyUtils.class.getResourceAsStream("/" + fileName));
    }

    public static <T> T getValIgnoreCase(Properties prop, String key) {
        if(prop == null || prop.isEmpty()) {
            return null;
        }
        Enumeration<?> enumeration = prop.propertyNames();
        while (enumeration.hasMoreElements()) {
            String keyName = (String) enumeration.nextElement();
            if(StringUtils.equalsIgnore(keyName, key)) {
                return (T) prop.get(keyName);
            }
        }
        return null;
    }
}
