package org.softwarevax.framework.context;

public class AnnotationApplicationContext extends AbstractApplicationContext {

    /**
     * 应用启动的端口
     */
    protected int port;

    public AnnotationApplicationContext(String[] packages) {
        super(packages);
    }

    @Override
    public String[] getPackages() {
        return this.packages;
    }

    /**
     * 为依赖注入的属性赋值
     * @return
     */
    /*public BeanDefinition assignmentDependOn(BeanDefinition definition, boolean isDynamicRegistry) {
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
    }*/
}
