1、spring-beans
```
1.1、spring-core
1.2、
```
2、spring-context
3、spring-core
4、spring-mvc
5、spring-mybatis
6、spring-test
7、spring-utils

day1:
1、创建一个容器，用于存放bean实例
1.1、容器使用Map存放，key为实例名，value为实例对象
1.2、bean对象的属性细化为：类，类实现的接口，以及继承的类，实例名称、创建方式(get方法、属性，参数等)、是否是主要的(多个相同的bean，注入主要的)，依赖的对象

day2:
2、扫描实例化的范围
2.1、确定范围的方式：注解、配置文件
2.2、扫描所有类