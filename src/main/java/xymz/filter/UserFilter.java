package xymz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
	//	HttpServletResponse  hsr = (HttpServletResponse) response ;
		HttpSession session = req.getSession();

	//	System.out.println("获取请求的参数:" + req.getQueryString());
		//System.out.println("获取请求的地址:" + req.getRequestURI());//-/xymz/user_login.action
		//System.out.println("获取请求的完整地址:" + req.getRequestURL());//-http://localhost:8080/xymz/user_login.action
		//System.out.println("没有工程名的URL地址:" + req.getServletPath());//--/user_login.action
		//System.out.println("获取工程名:" + req.getContextPath()); // --/xymz
		if (req.getServletPath().equals("/user_login.action")) {
			chain.doFilter(request, response);
		} else {
			if (session.getAttribute("user") == null) {

				
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
