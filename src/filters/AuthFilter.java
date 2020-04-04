package filters;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.LogingManagement;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/*" })
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		
		if(!LogingManagement.getInstance().checkIfLoggedIn(httpServletRequest.getSession().getId()))
		{
			if(!httpServletRequest.getRequestURL().toString().contains("login.jsp") &&
					!httpServletRequest.getRequestURL().toString().contains("register.jsp") &&
					!httpServletRequest.getRequestURL().toString().contains("AuthServlet") &&
					!httpServletRequest.getRequestURL().toString().contains("RegisterServlet"))
			{
				HttpServletResponse httpServletResponse = (HttpServletResponse)response;
				httpServletResponse.sendRedirect("login.jsp");
				return;
			}
		}
		else {
			if(httpServletRequest.getRequestURL().toString().contains("login.jsp") ||
					httpServletRequest.getRequestURL().toString().contains("register.jsp") ||
					httpServletRequest.getRequestURL().toString().contains("RegisterServlet"))
			{
				HttpServletResponse httpServletResponse = (HttpServletResponse)response;
				httpServletResponse.sendRedirect("homePage.jsp");
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
