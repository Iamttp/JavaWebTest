import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//ServletContextListener接口用于监听ServletContext对象的创建和销毁事件。
//实现了ServletContextListener接口的类都可以对ServletContext对象的创建和销毁进行监听。

public class MyServletContextListener implements ServletContextListener {

    // 当ServletContext被创建的时候(什么时候创建ServletContext呢？将web工程发布到web服务器里面去了，只要一启动web服务器，web服务器会针对每一个web应用创建ServletContext)，下面方法执行
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext被创建了！！！");
    }

    // 当ServletContext被销毁的时候(停止服务器，服务器就会把针对于每一个web应用的ServletContext摧毁)，下面方法执行
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext被销毁了！！！");
    }

}