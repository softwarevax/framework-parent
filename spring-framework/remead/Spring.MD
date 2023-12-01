# Spring

### 1、Spring IOC步骤

1.1、扫描组件

1.2、实例化存入容器

1.3、实例注入

### 2、扫描组件

被注解标记的类，即需要实例化的类，统称为组件。

1、扫描包：类集合 = function（包名），收集包下的所有类。

2、扫描类：组件集合 = function（类集合），判断类是否有组件的注解。

### 3、实例存入容器

1、解析组件：组件属性 = function（组件集合），解析每个组件，取出每个组件的属性。

2、实例化：组件实例 = function（组件属性），实例化组件，并存入容器中。

#### 3.1、组件的属性

1、isPrimary，是否是主要的。如果有@Primary注解，即是主要的，存在相同的实例时，会选择主要的。

2、beanName，实例名。关系到后面从容器中取数，默认是全限定类名。

3、bean，类实例，注入时需要取出的值。

4、dependsOn，被依赖的属性。即需要在哪些地方注入当前实例，哪个类的哪个属性，以何种方式注入等。

5、beanClass，实例化的类，用于实例化bean的值。

6、interfaces，当前类实现的接口。含从父类继承而来的，便于后面通过接口取出实例。

7、extendClass，继承的类。含从父类继承而来的，便于后面通过父类取出实例。

8、instanceType，注入方式。

9、isRefreshed，是否已经刷新。用于检测依赖该实例的属性，是否全部注入。

#### 3.2、依赖的组件属性

1、clazz，当前属性的类型。

2、ownerClass，当前属性所在的类名。

3、autowiredType，自动注入的方式，字段，方法，参数。不支持构造器方式，这样就不需要处理循环依赖的问题。

4、beanName，注入的实力名，即需要输入的实例，在Spring容器中的名字。

5、annotation，被标记的注解，目前仅有AutowiredVax.class，可配置

6、annotatedElement，被标记的元素，是字段，方法还是参数

7、injectMode，注入的方式，类型还是名称。

8、dependOnBean，依赖的实例，即需要注入的实例。

9、isRequired，是否是必须注入的，如果是，启动是找不到实例，程序启动失败，如果否，程序启动成功，但调用可能会提示空指针。

#### 3.3、注解

1、AutowiredVax，自动注入的注解，如需要将实例注入到当前属性中，在当前属性添加AutowiredVax注解

2、ComponentVax，组件注解，需要实例化的类，添加该注解，程序会实例化后，放入容器中，同ControllerVax，ServiceVax。

3、PrimaryVax，见3.1.1。

#### 3.4、事件

事件以组件的形式，最后被实例化和执行，可用于后面集成SpringMvc和Mybatis

### 4、实例注入

刷新容器，检查依赖注入的bean（即3.2.8）为空的实例，按照条件找到依赖的实例，为属性赋值。

### 5、运行结果

```
@SpringApplicationVax(packages = "org.softwarevax.framework.test")
public class TestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplicationRunner.run(TestApplication.class);
        DeployTaskServiceImpl testService = context.getBean(DeployTaskServiceImpl.class);
        String msg = testService.hello("world!");
        System.out.println(msg);
    }
}

hello world!
```

<a href="https://github.com/softwarevax/framework-parent/tree/main/spring-framework">传送门</a>

