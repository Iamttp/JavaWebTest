# Servlet 代码笔记

## 基本配置

Web工程设置

1. 创建javaweb工程 File --> New --> Project...

2. 选择Java Enterprise --> Web Application

3. 在WEB-INF 目录下点击右键，New --> Directory，创建 classes 和 lib 两个目录 classes目录用于存放编译后的class文件，lib用于存放依赖的jar包

4. File --> Project Structure...，进入 Project Structure窗口，点击 Modules --> 切换到 Paths 选项卡 --> 勾选 “Use module compile output path”，将 “Output path” 和 “Test output path” 都改为之前创建的classes目录

5. 点击 Modules --> 切换到 Dependencies 选项卡 --> 点击右边的“+”，选择 “JARs or directories...”，选择创建的lib目录

6. 配置打包方式Artifacts：点击 Artifacts选项卡，IDEA会为该项目自动创建一个名为“JavaWeb:war exploded”的打包方式，表示 打包成war包，并且是文件展开性的，输出路径为当前项目下的 out 文件夹，保持默认即可。另外勾选下“Build on make”，表示编译的时候就打包部署，勾选“Show content of elements”，表示显示详细的内容列表。

TomCat配置（已配置）

1. Run -> Edit Configurations，进入“Run Configurations”窗口，点击"+"-> Tomcat Server -> Local，创建一个新的Tomcat容器

2. 在"Name"处输入新的服务名，点击“Application server”后面的“Configure...”，弹出Tomcat Server窗口，选择本地安装的Tomcat目录 -> OK

之后就可以运行，然后访问
`http://localhost:8080/ServletTest_war_exploded/`

## Servlet 

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