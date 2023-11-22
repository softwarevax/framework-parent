package org.softwarevax.framework.mvc.config;

import org.softwarevax.framework.beans.annotation.ControllerVax;
import org.softwarevax.framework.context.ApplicationContext;
import org.softwarevax.framework.context.event.ApplicationContextEvent;
import org.softwarevax.framework.mvc.DefaultHttpInvoke;
import org.softwarevax.framework.mvc.MethodHandler;
import org.softwarevax.framework.mvc.annotations.UrlMapping;
import org.softwarevax.framework.mvc.exception.MappingExistsException;
import org.softwarevax.framework.rpc.BootstrapServer;
import org.softwarevax.framework.utils.*;

import java.lang.reflect.Method;
import java.util.*;

public abstract class SpringMvcContextConfigure implements ApplicationContextEvent {

    public final static String MVC_CLASSPATH_NAME = "mvc.properties";

    private final static String packages = "org.softwarevax.framework.mvc";

    private PackageScanner packageScanner;

    @Override
    public void onEvent(ApplicationContext ctx) {
        try {
            Properties prop = PropertyUtils.getClassPathProperties(MVC_CLASSPATH_NAME);
            packageScanner = new GenericPackagesScanner(ctx.getPackages());
            // 1、找到被controller注解标记的类
            Set<Class<?>> classes = packageScanner.getFullyQualifiedClassNameList();
            Set<Class<?>> webSet = new HashSet<>();
            Iterator<Class<?>> iterator = classes.iterator();
            while (iterator.hasNext()) {
                Class<?> next = iterator.next();
                if(AnnotationUtils.containsAnyAnnotation(next, ControllerVax.class)) {
                    webSet.add(next);
                }
            }
            // 2、解析处理方法
            Iterator<Class<?>> controller = webSet.iterator();
            List<MethodHandler> handlers = new ArrayList<>();
            while (controller.hasNext()) {
                Class<?> next = controller.next();
                String commonUrl = AnnotationUtils.get(next, UrlMapping.class);
                List<Method> methods = ClassUtils.getMarkedMethod(next, UrlMapping.class);
                Iterator<Method> methodIt = methods.iterator();
                while (methodIt.hasNext()) {
                    Method method = methodIt.next();
                    String subUri = AnnotationUtils.get(method, UrlMapping.class);
                    MethodHandler methodHandler = new MethodHandler();
                    methodHandler.setMethod(method);
                    methodHandler.setMethodURL(subUri);
                    methodHandler.setClazz(next);
                    methodHandler.setClazzUrl(commonUrl);
                    methodHandler.setObj(ctx.getBean(next));
                    methodHandler.setUrl(PathUtils.merge(commonUrl, subUri));
                    methodHandler.setParameters(ClassUtils.getMethodParameters(method));
                    if(handlers.contains(methodHandler)) {
                        throw new MappingExistsException("method mapping exists");
                    }
                    handlers.add(methodHandler);
                }
            }
            DefaultHttpInvoke invoke = new DefaultHttpInvoke(handlers, ctx);
            new Thread(() -> {
                BootstrapServer server = new BootstrapServer(Integer.parseInt(prop.getProperty("port")), invoke);
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return 9000;
    }
}
