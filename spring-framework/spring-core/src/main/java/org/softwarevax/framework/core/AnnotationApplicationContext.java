package org.softwarevax.framework.core;

import org.softwarevax.framework.beans.GlobalConstants;
import org.softwarevax.framework.beans.definition.DependOnDefinition;
import org.softwarevax.framework.beans.definition.DependOnDefinitionParser;
import org.softwarevax.framework.beans.definition.GenericDependOnDefinitionParser;
import org.softwarevax.framework.beans.factory.BeanDefinition;
import org.softwarevax.framework.beans.factory.BeansException;
import org.softwarevax.framework.beans.factory.GenericBeanDefinition;
import org.softwarevax.framework.beans.factory.InjectMode;
import org.softwarevax.framework.utils.*;

import java.util.*;
import java.util.stream.Collectors;

public class AnnotationApplicationContext implements ApplicationContext {

    protected int port;

    /**
     * spring扫描的包
     */
    protected String[] packages;

    /**
     * 加载packages下的所有类
     */
    protected PackageScanner packageScanner = new GenericPackagesScanner();

    /**
     * 扫描包下的所有被标记的类
     */
    protected Set<Class<?>> classes = new HashSet<>();

    /**
     * spring 容器
     */
    private Map<String, BeanDefinition> beanMap = new HashMap<>();

    private DependOnDefinitionParser parser = new GenericDependOnDefinitionParser();

    public AnnotationApplicationContext() {
    }

    public AnnotationApplicationContext(String[] packages) {
        this.packages = packages;
        packageScanner.setPackages(packages);
        // 1、获取指定包下的所有类
        this.classes = packageScanner.getFullyQualifiedClassNameList();
        Iterator<Class<?>> iterator = this.classes.iterator();
        // 2、移除没有被注解标记的类
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            if(!AnnotationUtils.containsAnyAnnotation(clazz, GlobalConstants.COMPONENT_ANNOTATIONS)) {
                iterator.remove();
            }
        }
        // 3、初始化bean
        for(Class<?> clazz : classes) {
            this.initBeanDefinition(clazz);
        }
        // 4、给依赖赋值
        for(BeanDefinition beanDefinition : beanMap.values()) {
            this.assignmentDependOn(beanDefinition, true);
        }
        // 5、事件通知
        List<ApplicationContextEventAware> events = getBeans(ApplicationContextEventAware.class);
        events = events.stream().sorted(Comparator.comparing(Order::getOrder)).collect(Collectors.toList());
        events.stream().forEach(row -> row.onEvent(this));
        // 6、刷新依赖
        this.refreshDependOns();
    }

    public String[] getPackages() {
        return this.packages;
    }

    @Override
    public Set<Class<?>> getClasses(Class<?> clazz) {
        if(clazz == null)  {
            return this.classes;
        }
        Set<Class<?>> set = new HashSet<>();
        Iterator<Class<?>> iterator = this.classes.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            if(next.isAssignableFrom(clazz)) {
                set.add(next);
            }
        }
        return set;
    }

    @Override
    public Object getBean(String key) throws BeansException {
        return beanMap.get(key).getBean();
    }

    @Override
    public <T> List<T> getBeans(Class<T> clazz) throws BeansException {
        List<BeanDefinition> beanDefinitions = getBeanDefinitions(clazz);
        Assert.isTrue(!CollectionUtils.isEmpty(beanDefinitions), "容器中不存在符合条件的实例，class: " + clazz);
        List<T> beans = new ArrayList<>();
        beanDefinitions.stream().forEach(row -> beans.add((T) row.getBean()));
        return beans;
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws BeansException {
        List<T> beans = getBeans(clazz);
        Assert.isTrue(CollectionUtils.length(beans) == 1, "容器中超过一个符合条件的实例");
        return beans.get(0);
    }

    @Override
    public <T> T registry(String beanName, Class<T> clazz) throws BeansException {
        BeanDefinition definition = this.initBeanDefinition(beanName, clazz);
        refreshDependOns();
        return (T) this.assignmentDependOn(definition, true).getBean();
    }

    @Override
    public <T> T registry(Class<T> clazz) throws BeansException {
        return registry(null, clazz);
    }

    @Override
    public <T> T registry(T bean) {
        BeanDefinition definition = new GenericBeanDefinition(bean);
        beanMap.put(definition.getBeanName(), definition);
        refreshDependOns();
        return (T) definition.getBean();
    }

    @Override
    public <T> T registry(String beanName, T bean) {
        BeanDefinition definition = new GenericBeanDefinition(bean);
        beanMap.put(beanName, definition);
        refreshDependOns();
        return (T) definition.getBean();
    }

    @Override
    public BeanDefinition getDefinition(String clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 为依赖注入的属性赋值
     * @return
     */
    public BeanDefinition assignmentDependOn(BeanDefinition definition, boolean isDynamicRegistry) {
        // 1、获取待注入bean的依赖
        List<DependOnDefinition> dependsOns = definition.getDependsOn();
        if(CollectionUtils.isEmpty(dependsOns)) { // 如果没有依赖，返回
            return definition;
        }
        for(DependOnDefinition dependOn : dependsOns) { // 如果有依赖，依次为依赖赋值
            if(dependOn.getDependOnBean() != null) {
                continue;
            }
            // 待注入的bean
            BeanDefinition injectBean = null;
            if(dependOn.getInjectMode() == InjectMode.TYPE) { // 如果是按照类型注入
                // 方法、属性、构造器，方法参数等
                Class<?> clazz = ClassUtils.getType(dependOn.getAnnotatedElement());
                List<BeanDefinition> beans = getBeanDefinitions(clazz);
                Assert.isTrue(!CollectionUtils.isEmpty(beans) || isDynamicRegistry || !dependOn.isRequired(), "容器中不存在符合条件的实例, 注入方式: [类型注入], 注入类型" + clazz.getCanonicalName());
                if(CollectionUtils.length(beans) > 1) {
                    beans = getPrimaryBeanDefinitions(beans);
                    Assert.isTrue(CollectionUtils.length(beans) == 1, "容器中超过一个符合条件的实例, 注入方式: [类型注入], 注入类型: " + clazz.getCanonicalName());
                } else if(CollectionUtils.isEmpty(beans)) {
                    beans.add(new GenericBeanDefinition(clazz));
                }
                injectBean = beans.get(0);
            } else if(dependOn.getInjectMode() == InjectMode.NAME) {
                injectBean = beanMap.get(dependOn.getBeanName());
            }
            BeanDefinition ownerBean = beanMap.get(dependOn.getOwnerBeanName());
            dependOn.setDependOnBean(injectBean.getBean());
            Assert.notNull(injectBean, "No bean:[" + dependOn.getBeanName() + "] were found in the Spring container");
            Assert.notNull(ownerBean, "No bean:[" + ownerBean.getBeanName() + "] were found in the Spring container");
            Object obj = ObjectUtils.set(ownerBean.getBean(), injectBean.getBeanClass(), dependOn.getAnnotatedElement(), injectBean.getBean());
            ownerBean.setBean(obj);
        }
        return definition;
    }

    private List<BeanDefinition> getBeanDefinitions(Class<?> clazz) {
        List<BeanDefinition> beans = new ArrayList<>();
        Iterator<BeanDefinition> iterator = beanMap.values().iterator();
        while (iterator.hasNext()) {
            BeanDefinition next = iterator.next();
            if(clazz.isAssignableFrom(next.getBeanClass())) {
                beans.add(next);
            }
        }
        return beans;
    }

    private List<BeanDefinition> getPrimaryBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        if(CollectionUtils.isEmpty(beanDefinitions)) {
            return null;
        }
        List<BeanDefinition> beans = beanDefinitions.stream().filter(row -> row.isPrimary()).collect(Collectors.toList());
        return beans;
    }

    private BeanDefinition initBeanDefinition(String beanName, Class<?> clazz) {
        parser.setClass(clazz);
        BeanDefinition beanDefinition = new GenericBeanDefinition(clazz);
        beanDefinition.setDependsOn(parser.dependOnDefinitions());
        if(!StringUtils.isBlank(beanName)) {
            beanDefinition.setBeanName(beanName);
        }
        Assert.isTrue(!beanMap.containsKey(beanDefinition.getBeanName()), "容器中已存在名称为[" + beanDefinition.getBeanName() + "]的实例");
        beanMap.put(beanDefinition.getBeanName(), beanDefinition);
        return beanDefinition;
    }

    private BeanDefinition initBeanDefinition(Class<?> clazz) {
        return initBeanDefinition(null, clazz);
    }

    private void refreshDependOns() {
        // 刷新依赖，保证动态注入的实例依赖被赋值
        for(BeanDefinition beanDefinition : beanMap.values()) {
            this.assignmentDependOn(beanDefinition, false);
        }
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }
}
