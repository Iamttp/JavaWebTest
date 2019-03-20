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

