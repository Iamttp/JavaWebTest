# Servlet 代码笔记

## 一、基本配置

### Web工程设置

1. 创建javaweb工程 File --> New --> Project...

2. 选择Java Enterprise --> Web Application

3. 在WEB-INF 目录下点击右键，New --> Directory，创建 classes 和 lib 两个目录 classes目录用于存放编译后的class文件，lib用于存放依赖的jar包

4. File --> Project Structure...，进入 Project Structure窗口，点击 Modules --> 切换到 Paths 选项卡 --> 勾选 “Use module compile output path”，将 “Output path” 和 “Test output path” 都改为之前创建的classes目录

5. 点击 Modules --> 切换到 Dependencies 选项卡 --> 点击右边的“+”，选择 “JARs or directories...”，选择创建的lib目录

6. 配置打包方式Artifacts：点击 Artifacts选项卡，IDEA会为该项目自动创建一个名为“JavaWeb:war exploded”的打包方式，表示 打包成war包，并且是文件展开性的，输出路径为当前项目下的 out 文件夹，保持默认即可。另外勾选下“Build on make”，表示编译的时候就打包部署，勾选“Show content of elements”，表示显示详细的内容列表。

### TomCat配置（已配置）

1. Run -> Edit Configurations，进入“Run Configurations”窗口，点击"+"-> Tomcat Server -> Local，创建一个新的Tomcat容器

2. 在"Name"处输入新的服务名，点击“Application server”后面的“Configure...”，弹出Tomcat Server窗口，选择本地安装的Tomcat目录 -> OK

之后就可以运行，然后访问`http://localhost:8080/ServletTest_war_exploded/`

出现Address localhost:8080 is already in use
解决方法：
1. cmd中执行netstat -ano 找到0.0.0.0:8080，记住它的PID
2. 任务管理器-》详细信息-》结束上面PID的任务

## 二、Servlet简介 

部署servlet
方法一：
    在WEB-INF目录下web.xml文件的<web-app>标签中添加如下内容：
```xml
<servlet>  
    <servlet-name>HelloWorld</servlet-name>  
    <servlet-class>HelloWorld</servlet-class>  
</servlet>  
  
<servlet-mapping>  
    <servlet-name>HelloWorld</servlet-name>  
    <url-pattern>/HelloWorld</url-pattern>  
</servlet-mapping>  
```

方法二：
在HelloWorld文件的类前面加上：@WebServlet("/HelloWorld")


* 将`javax.servlet.http.HttpServlet`子类化会更有意义
* 部署描述符只是命名为web.xml，并放在WEB-INF目录下。

如图，Tomcat 包含了核心服务模块：Connector连接模块 和 Container 容器。Tomcat Server 核心是一个 Servlet/JSP Container。对每一个HTTP请求，过程如下

![avatar](/Tomcat.png)

— 获取连接

— Servlet来分析请求（HttpServletRequest）

— 调用其service方法，进行业务处理

— 产生相应的响应（HttpServletResponse）

— 关闭连接

## 三、Servlet 监听器

监听器其实就是一个实现特定接口的普通java程序，这个程序专门用于监听另一个java对象的方法调用或属性改变，当被监听对象发生上述事件后，监听器某个方法立即被执行。

### 观察者设计模式(observer设计模式)

* java的事件监听机制涉及到三个组件：事件源、事件监听器、事件对象。

1. 我们一开始会简单地设计一个Person对象，具体代码如下：

```java
class Person {
    public void run() {
        System.out.println("run!!!");
    }
    public void eat() {
        System.out.println("eat!!!");
    }
}
```

2. 对外暴露一个接口。

```java
/*
 * 对外暴露一个接口，别人实现这个接口，我针对这个接口进行调用
 * 那这个接口里有几个方法呢？
 * 答：我这个人身上有2个动作想被别人监听，所以我这个接口针对这2个动作定义2个相对应的方法。
 */
interface PersonListener {
    public void dorun();
    public void doeat();
}
```

3. Person的具体代码应为：

```java
class Person {
    private PersonListener listener;
    public void registerListener(PersonListener listener) {
        this.listener = listener;
    }

    public void run() {
        if (listener != null) { // 别人传进来监听器，那就先调用监听器相对应的方法处理一下，再调用自己的方法
            Even even = new Even(this);
            this.listener.dorun(even);
        }
        System.out.println("run!!!");
    }

    public void eat() {
        if (listener != null) {
            Even even = new Even(this);
            this.listener.doeat(even);
        }
        System.out.println("eat!!!");
    }
}

/*
 * 对外暴露一个接口，别人实现这个接口，我针对这个接口进行调用
 * 那这个接口里有几个方法呢？
 * 答：我这个人身上有2个动作想被别人监听，所以我这个接口针对这2个动作定义2个相对应的方法。
 */
interface PersonListener {
    public void dorun(Even even);
    public void doeat(Even even);
}

class Even {
    private Person person;
    public Even() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Even(Person person) {
        super();
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
```

4. 经过这样的设计之后，Peron类的对象就可以被其他对象监听了。测试代码如下：

```java
public class Demo3 {

    public static void main(String[] args) {
        Person p = new Person();
        p.registerListener(new MyListener());
        p.eat();
        p.run();
    }
}

class MyListener implements PersonListener {
    @Override
    public void dorun(Even even) {
        System.out.println(even.getPerson() + "你吃完就跑，有病！！！");
    }

    @Override
    public void doeat(Even even) {
        System.out.println(even.getPerson() + "你天天吃，你就知道吃，你猪啊！！！");
    }
}
```

### 监听ServletContext域对象的创建和销毁

* 当ServletContext对象被创建时，激发contextInitialized (ServletContextEvent sce)方法。
* 当ServletContext对象被销毁时，激发contextDestroyed(ServletContextEvent sce)方法。

应用：
1. web应用一启动时，希望启动一些定时器来定时的执行某些任务。只要把启动定时器的代码写到contextInitialized方法里面，这个web应用一启动，定时器就启动了。
2. 我们以后会学Spring框架，其实Spring的启动代码就是写在一个ServletContext监听器的contextInitialized方法里面的。Spring是一个框架，我们希望web应用一启动的时候，就把Spring框架启动起来。

### 监听HttpSession域对象的创建和销毁

* 创建一个Session时，激发sessionCreated(HttpSessionEvent se)方法。
* 销毁一个Session时，激发sessionDestroyed(HttpSessionEvent se)方法。

* 创建：用户每一次访问时，服务器创建session。
* 销毁：如果用户的session30分钟没有使用，服务器就会销毁session，我们在web.xml里面也可以配置session失效时间。
  
应用：
1. 一般来说，用户都会开一个浏览器访问服务器，只要他一访问，服务器就会针对他创建一个session，每个用户就有一个session，在实际开发里面，只要统计内存里面有多少session，就能知道当前有多少在线人数。为了统计当前在线人数，这时可以写一个这样的监听器，只要有一个session被创建就让一个变量count+1，session被销毁就让变量count-1，输出count这个值，就能知道当前有多少在线人数。 
 
### 监听ServletRequest域对象的创建和销毁

* Request对象被创建时，监听器的requestInitialized(ServletRequestEvent sre)方法将会被调用。
* Request对象被销毁时，监听器的requestDestroyed(ServletRequestEvent sre)方法将会被调用。

应用：
1. 我们可以在requestInitialized(ServletRequestEvent sre)方法里面加上一句代码：count++，输出count的值，就可以统计网站一天的点击量。
2.  我们也可以在requestInitialized(ServletRequestEvent sre)方法里面加上一句代码：sre.getServletRequest().getRemoteAddr()。可以知道当前这个请求是由哪个IP发出来的，在后台可以通过这个监听器监听到哪些IP在给你发请求，这样做的目的是为了防止坏人，有些坏人恶意点击，写机器人点击，在后台写这样的一个监听器可以监听到某个时间段有某个IP重复点击，如果发生这种情况，就说明这个人是坏人，就可以屏蔽其IP。

## 四、Servlet 过滤器

![avatar](/Filter.png)

> 过滤器是处于客户端与服务器资源文件之间的一道过滤网，
>
> 在访问资源文件之前，通过一系列的过滤器对请求进行修改、判断等
>
> 把不符合规则的请求在中途拦截或修改。也可以对响应进行过滤，拦截或修改响应。
>

* 服务器在加载的时候按：监听器>过滤器>Servlet的顺序加载
* 过滤器一般用于登录权限验证、资源访问权限控制、敏感词汇过滤、字符编码转换等等操作，便于代码重用，不必每个servlet中还要进行相应的操作。

1. 新建一个类，实现Filter接口
2. 实现doFilter()方法
3. 在web.xml中进行配置（参照Servlet配置）

应用：
1. 通过控制对chain.doFilter的方法的调用，来决定是否需要访问目标资源。
2. 通过在调用chain.doFilter方法之前，做些处理来达到某些目的。
3. 通过在调用chain.doFilter方法之后，做些处理来达到某些目的。