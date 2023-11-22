package org.softwarevax.framework.mybatis.config;

import org.softwarevax.framework.context.ApplicationContext;
import org.softwarevax.framework.context.event.ApplicationContextEvent;
import org.softwarevax.framework.mybatis.GlobalConstants;
import org.softwarevax.framework.mybatis.bean.Constants;
import org.softwarevax.framework.mybatis.jdbc.JdbcManager;
import org.softwarevax.framework.mybatis.jdbc.JdbcProperties;
import org.softwarevax.framework.mybatis.proxy.MapperProxyFactory;
import org.softwarevax.framework.mybatis.proxy.ProxyUtils;
import org.softwarevax.framework.mybatis.utils.PropertyUtils;
import org.softwarevax.framework.utils.AnnotationUtils;
import org.softwarevax.framework.utils.GenericPackagesScanner;
import org.softwarevax.framework.utils.PackageScanner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public abstract class MapperJdbcConfigurer implements ApplicationContextEvent {

    public final static String JDBC_CLASSPATH_NAME = "jdbc.properties";

    private PackageScanner packageScanner = new GenericPackagesScanner();

    private Set<Class<?>> classes = new HashSet<>();

    @Override
    public void onEvent(ApplicationContext ctx) {
        JdbcManager jdbcManager = new JdbcManager(getJdbcProperties());
        packageScanner.setPackages(ctx.getPackages());
        this.classes = packageScanner.getFullyQualifiedClassNameList();
        Iterator<Class<?>> iterator = this.classes.iterator();
        // 2、移除没有被注解标记的类
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            if(!AnnotationUtils.containsAnyAnnotation(clazz, GlobalConstants.MAPPER_ANNOTATIONS)) {
                iterator.remove();
                continue;
            }
            ctx.registry(clazz.getSimpleName(), ProxyUtils.getProxy(clazz, new MapperProxyFactory(jdbcManager)));
        }
        ctx.refresh(false);
    }

    public JdbcProperties getJdbcProperties() {
        JdbcProperties jdbcProperties = new JdbcProperties();
        try {
            Properties properties = PropertyUtils.getClassPathProperties(JDBC_CLASSPATH_NAME);
            jdbcProperties.setClassName(properties.getProperty(Constants.JDBC_CLASS_NAME));
            jdbcProperties.setUrl(properties.getProperty(Constants.JDBC_URL));
            jdbcProperties.setUserName(properties.getProperty(Constants.JDBC_USER_NAME));
            jdbcProperties.setPassword(properties.getProperty(Constants.JDBC_PASSWORD));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return jdbcProperties;
    }
}
