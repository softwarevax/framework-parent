## 手动实现MyBatis

#### 1、代理模式

要实现MyBatis，代理模式必不可少。代理，字面解释就是事物A帮助事物B干活。代理模式分为静态代理和动态代理，静态代理就是A只帮B干活，动态代理是A不仅帮B干活，还会帮C、D，帮助对象是不确定的。

##### 1.1、静态代理

代购，就是找人代理购买需要的商品。

```
public interface Action {
    /**
     * 购物
     */
    void shop();
}

// 消费者，真正出钱的人
public class Consumer implements Action {
    @Override
    public void shop() {
        System.out.println("购物");
    }
}

// 代购
public class Agent implements Action {

    //消费者
    private Consumer consumer;

    public Agent() {
    }

    public Agent(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void shop() {
        consumer.shop();
    }
}

public class Test {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        Agent agent = new Agent(consumer);
        agent.shop();
    }
}

输出结果：购物
```

##### 1.2、动态代理

如果你是代购，发现给更多的人做代购，能赚更多的钱，只要是需要购买(shop)东西的人，你就可以给他们做代购。

```
public interface Action {
    /**
     * 购物
     */
    void shop();
}

// 代购
public class Agent implements Action {

    //消费者
    private Action action;

    @Override
    public void shop() {
        action.shop();
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

// 第一个消费者
public class Consumer1 implements Action {
    @Override
    public void shop() {
        System.out.println("购物1");
    }
}

// 第二个消费者
public class Consumer2 implements Action {
    @Override
    public void shop() {
        System.out.println("购物2");
    }
}

public class Test {
    public static void main(String[] args) {
        Consumer1 consumer1 = new Consumer1();
        Consumer2 consumer2 = new Consumer2();
        Agent agent = new Agent();
        // 为第一个消费者服务(代购)
        agent.setAction(consumer1);
        agent.shop();
        // 为第二个消费者服务(代购)
        agent.setAction(consumer2);
        agent.shop();
    }
}

输出结果：
购物1
购物2
```

##### 1.3、JDK动态代理

基于 JDK 的动态代理需要被代理的对象有接口，注意不是JDK的动态代理需要实现了接口的类。这两者的区别，第一种是代理的可以是一个知道要做什么的接口，即使还没有具体的实现类，第二种是已经有具体的实现类了。

先利用Jdk实现第二种(已经有具体实现类，Consumer1和Consumer2)，利用Jdk实现第一种，详见Jdk实现MyBatis

```
// Jdk实现的代理，等同于上面的Agent
public class JdkAgent implements InvocationHandler {

    private Action action;

    public JdkAgent(Action action) {
        this.action = action;
    }

    public Action getProxy() {
        return (Action)Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                action.getClass().getInterfaces(), 
                this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(action, args);
    }
}

public class Test {
    public static void main(String[] args) {
        Consumer1 consumer1 = new Consumer1();
        Consumer2 consumer2 = new Consumer2();

        Action action1 = new JdkAgent(consumer1).getProxy();
        // 为第一个消费者服务(代购)
        action1.shop();

        Action action2 = new JdkAgent(consumer2).getProxy();
        // 为第一个消费者服务(代购)
        action2.shop();
    }
}

输出结果：
购物1
购物2
```

说明：其他没有变动的类代码没有展示。

#### 2、Jdk实现MyBatis

##### 2.1、Mapper

准备好接口，并表明接口需要实现的功能

```
public interface DeployTaskMapper {

    @SelectVax("select * from deploy_task")
    List<Object> queryList();
}
```

##### 2.2、Jdbc查询

为了简洁，代码尽可能地写在了一个类中，主要是实现功能

```
public class JdbcManager {

    private Connection conn;

    private String className = "com.mysql.cj.jdbc.Driver";

    private String url = "jdbc:mysql://localhost:3306/test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private String userName = "root";

    private String password = "123456";


	// jdbc连接
    {
        try {
            Class.forName(className);
            this.conn = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException var1) {
            var1.printStackTrace();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }
    }

	// 实现sql查询
    public List<Map<String, Object>> statement(String sql) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Map<String, Object>> rows = new ArrayList<>();
            while (resultSet.next()) {
                // 遍历行
                Map<String, Object> row = new HashMap<>();
                for(int i = 1; i <= columnCount; i++) {
                    // 遍历列
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                rows.add(row);
            }
            return rows;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
```

##### 2.3、Jdk的动态代理

```
public class JdkAgent implements InvocationHandler {

    private Object obj;

    public JdkAgent(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    	// 解析出Mapper方法上的sql
        String sql = method.getAnnotation(SelectVax.class).value();
        if(obj instanceof JdbcManager) {
            JdbcManager template = (JdbcManager) obj;
            // 执行SQL，并返回结果
            return template.statement(sql);
        }
        return null;
    }
}
```

##### 2.4、执行代码

从代码可知，DeployTaskMapper其实只起到两个作用，一个是JDk动态代理需要一个接口，二是说明接口要实现的功能(SQL)

```
public class Test {
    public static void main(String[] args) {
        JdbcManager manager = new JdbcManager();
        JdkAgent agent = new JdkAgent(manager);
        DeployTaskMapper mapper = (DeployTaskMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {DeployTaskMapper.class}, agent);
        List<Object> objects = mapper.queryList();
        System.out.println(objects);
    }
}

输出结果：
[{task_name=1, fqn=1, user_id=1, property_key=1, task_id=1, option_id=2, remark=1, id=1, property_value=1}]
```

<a href="https://github.com/softwarevax/framework-parent/tree/main/spring-framework/spring-test">传送门（jdk动态代理在mybatistest包下）</a>