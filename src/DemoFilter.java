import javax.servlet.*;
import java.io.IOException;

public class DemoFilter implements Filter {
    private FilterConfig filterConfig;

    //Filter初始化时的回调方法
    //FilterConfig接口实例中封装了这个Filter的初始化参数
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    //主要负责过滤工作
    //FilterChain用于调用过滤器链中的下一个过虑器
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取此Filter的初始化参数的值
        String encoding = filterConfig.getInitParameter("encoding");
        if (null != encoding && !"".equals(encoding)){
            //设置请求数据的编码方式
            servletRequest.setCharacterEncoding(encoding);
            System.out.println("请求数据已编码");
        }
        //把请求和响应对象传给过滤器链中的下一个要调用的过滤器或Servlet
        filterChain.doFilter(servletRequest,servletResponse);
    }


    //此Filter被释放时的回调方法
    @Override
    public void destroy() {
        //空
    }
}
