package org.softwarevax.framework.context;

import org.softwarevax.framework.beans.GlobalConstants;
import org.softwarevax.framework.beans.definition.BeanParser;
import org.softwarevax.framework.beans.definition.DependOnDefinition;
import org.softwarevax.framework.beans.definition.GenericBeanParser;
import org.softwarevax.framework.beans.factory.BeanDefinition;
import org.softwarevax.framework.beans.factory.BeansException;
import org.softwarevax.framework.beans.factory.InjectMode;
import org.softwarevax.framework.context.event.ApplicationContextEvent;
import org.softwarevax.framework.utils.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class AbstractApplicationContext implements ApplicationContext {

    /**
     * spring扫描的包
     */
    protected String[] packages;

    /**
     * 加载packages下的所有类
     */
    protected PackageScanner packageScanner;

    /**
     * 扫描包下的所有被标记的类
     */
    protected Set<Class<?>> classes = new HashSet<>();

    /**
     * bean转化和解析
     */
    protected BeanParser beanParser = new GenericBeanParser();

    /**
     * spring 容器
     */
    private Map<String, BeanDefinition> beanMap = new ConcurrentHashMap<>();

    /**
     * 事件
     */
    private List<ApplicationContextEvent> events;

    public AbstractApplicationContext(String[] packages) {
        this.packages = packages;
        this.packageScanner = new GenericPackagesScanner(packages);
        // 1、获取指定包下的所有类
        Set<Class<?>> classes = packageScanner.getFullyQualifiedClassNameList();
        this.parseClazz(classes);
        // 刷新容器，为依赖赋值
        refresh(false);
        this.events = this.addApplicationEvent();
    }

    @Override
    public boolean parseClazz(Set<Class<?>> classes) {
        this.classes.addAll(classes);
        Iterator<Class<?>> iterator = classes.iterator();
        // 2、移除没有被注解标记的类
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            if(!AnnotationUtils.containsAnyAnnotation(clazz, GlobalConstants.COMPONENT_ANNOTATIONS)) {
                iterator.remove();
            }
        }
        // 3、解析被标记的类
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = beanParser.parseBeanDefinition(clazz);
            beanParser.instantiation(beanDefinition);
            beanMap.put(beanDefinition.getBeanName(), beanDefinition);
        }
        return true;
    }

    @Override
    public boolean refresh(boolean finishRefresh) {
        List<BeanDefinition> definitions = beanMap.values().stream().filter(row -> !row.isRefreshed()).collect(Collectors.toList());
        for (BeanDefinition ownerBean :definitions) {
            List<DependOnDefinition> dependsOn = ownerBean.getDependsOn();
            for (DependOnDefinition definition : dependsOn) {
                InjectMode injectMode = definition.getInjectMode();
                BeanDefinition injectBean = null;
                switch (injectMode) {
                    case TYPE: {
                        // 根据类型获取实例
                        List<BeanDefinition> beanDefinitions = getBeanDefinitions(definition.getClazz());
                        //Assert.notEmpty(beanDefinitions, "没有同类型" + definition.getClazz().getCanonicalName() + "实例, " + ownerBean.getBeanClass().getCanonicalName() + "实例化失败");
                        if(CollectionUtils.length(beanDefinitions) == 1) {
                            // 同类型的实例仅有一个
                            injectBean = beanDefinitions.get(0);
                        } else {
                            // 同类型的实例有多个, 如果有@Primary注解的仅有一个，则使用有@Primary注解的实例
                            List<BeanDefinition> beans = beanDefinitions.stream().filter(row -> row.isPrimary()).collect(Collectors.toList());
                            Assert.isTrue(CollectionUtils.length(beans) == 1 || !finishRefresh, "同类型没有或存在多个实例");
                            injectBean = CollectionUtils.length(beans) > 0 ? beans.get(0) : null;
                        }
                        break;
                    }
                    case NAME: {
                        // 被注入的实力在容器中的名称
                        String beanName = definition.getBeanName();
                        Assert.isTrue(this.beanMap.containsKey(beanName), "不存在实例:" + beanName);
                        injectBean = beanMap.get(beanName);
                    }
                    default: break;
                }
                if(!Objects.isNull(injectBean)) {
                    ObjectUtils.set(ownerBean.getBean(), injectBean.getBeanClass(), definition.getAnnotatedElement(), injectBean.getBean());
                }
            }
        }
        return true;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Assert.notNull(beanMap.containsKey(beanName), "不存在实例:" + beanName);
        return beanMap.get(beanName).getBean();
    }

    @Override
    public <T> List<T> getBeans(Class<T> clazz) throws BeansException {
        List<BeanDefinition> beanDefinitions = getBeanDefinitions(clazz);
        Assert.notEmpty(beanDefinitions, "容器中不存在符合条件的实例，class: " + clazz);
        return beanDefinitions.stream().map(row -> (T) row.getBean()).collect(Collectors.toList());
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws BeansException {
        List<T> beans = getBeans(clazz);
        Assert.isTrue(CollectionUtils.length(beans) == 1, "容器中超过一个符合条件的实例");
        return beans.get(0);
    }

    @Override
    public <T> T registry(String beanName, Class<T> clazz) throws BeansException {
        BeanDefinition beanDefinition = beanParser.parseBeanDefinition(clazz);
        beanParser.instantiation(beanDefinition);
        if(!StringUtils.isBlank(beanName)) {
            beanDefinition.setBeanName(beanName);
        }
        beanMap.put(beanDefinition.getBeanName(), beanDefinition);
        refresh(false);
        return (T) beanDefinition.getBean();
    }

    @Override
    public <T> T registry(Class<T> clazz) throws BeansException {
        return registry(null, clazz);
    }

    @Override
    public <T> T registry(T bean) {
        BeanDefinition definition = beanParser.parseBeanDefinition(bean);
        definition.setBean(bean);
        beanMap.put(definition.getBeanName(), definition);
        refresh(false);
        return (T) definition.getBean();
    }

    @Override
    public <T> T registry(String beanName, T bean) {
        BeanDefinition definition = beanParser.parseBeanDefinition(bean);
        definition.setBean(bean);
        beanMap.put(beanName, definition);
        refresh(false);
        return (T) definition.getBean();
    }

    @Override
    public BeanDefinition getDefinition(String clazz) {
        return beanMap.get(clazz);
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

    @Override
    public String[] getPackages() {
        return packages;
    }

    private List<ApplicationContextEvent> addApplicationEvent() {
        List<ApplicationContextEvent> list = new ArrayList<>();
        Set<Class<?>> events = new HashSet<>();
        Collection<BeanDefinition> values = this.beanMap.values();
        Iterator<BeanDefinition> iterator = values.iterator();
        while (iterator.hasNext()) {
            BeanDefinition next = iterator.next();
            List<Class<?>> interfaces = next.getInterfaces();
            for (Class<?> clazz : interfaces) {
                if(clazz.isAssignableFrom(ApplicationContextEvent.class)) {
                    events.add(next.getBeanClass());
                    break;
                }
            }
        }
        Iterator<Class<?>> it = events.iterator();
        while (it.hasNext()) {
            Class<?> next = it.next();
            ApplicationContextEvent event = (ApplicationContextEvent) BeanUtils.newInstance(next);
            list.add(event);
        }
        for (ApplicationContextEvent event : list) {
            registry(event);
            event.onEvent(this);
        }
        return list;
    }
}
