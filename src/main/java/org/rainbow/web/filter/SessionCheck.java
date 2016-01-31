package org.rainbow.web.filter;

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

import org.hx.rainbow.common.exception.SysException;
import org.hx.rainbow.common.web.session.ThreadConstants;
import org.rainbow.web.common.RedirectUtil;

public class SessionCheck implements Filter{

	private String loginUrl;
	
	
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession(false);
		
		if (session != null) {
			
			if(session.getAttribute(ThreadConstants.RAINBOW_USER) == null){
				if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
					response.setHeader("sessionstatus", "timeout"); 
					return;
				}
				RedirectUtil.getInstance().sendRedirect(request, response, this.loginUrl);
				return;
			}
			
			chain.doFilter(request, response);
		}else{
			if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
				response.setHeader("sessionstatus", "timeout"); 
				return;
			}
			RedirectUtil.getInstance().sendRedirect(request, response, this.loginUrl);
			return ;
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 String loginUrl = filterConfig.getInitParameter("loginUrl");
		 
	     if(loginUrl == null || loginUrl.isEmpty()){
	    	 throw new SysException("The fitler SessionCheck's initparm loginUrl is null or empty!");
	     }
	     
	     this.loginUrl = loginUrl;
	}
	
	



	@Override
	public void destroy() {
		
	}
}
