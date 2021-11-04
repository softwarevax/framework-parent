package org.softwarevax.framework.mybatis.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    /**
     * 获取classpath配置属性
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Properties getClassPathProperties(String fileName) throws IOException {
        return getProperties(PropertyUtils.class.getResourceAsStream("/" + fileName));
    }
}
