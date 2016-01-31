package org.rainbow.web.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectUtil {
	
	private static volatile RedirectUtil  redirectUtil = null;
	
	private RedirectUtil(){};
	
	public static RedirectUtil getInstance(){
		if(redirectUtil == null){
			synchronized (RedirectUtil.class) {
				if(redirectUtil == null){
					redirectUtil = new RedirectUtil();
				}
			}
		}
		return redirectUtil;
	}
	
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response,
			String url, boolean useRelativeContext) throws IOException {
		String redirectUrl = calculateRedirectUrl(request.getContextPath(), url, useRelativeContext);
		redirectUrl = response.encodeRedirectURL(redirectUrl);
		
		response.sendRedirect(redirectUrl);
	}
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response,
			String url) throws IOException {
		String redirectUrl = calculateRedirectUrl(request.getContextPath(), url, false);
		redirectUrl = response.encodeRedirectURL(redirectUrl);
		response.sendRedirect(redirectUrl);
	}

	private String calculateRedirectUrl(String contextPath, String url, boolean useRelativeContext) {
		if (!UrlUtils.isAbsoluteUrl(url)) {
			if (useRelativeContext) {
				return url;
			}
			else {
				return contextPath + url;
			}
		}

		if (!useRelativeContext) {
			return url;
		}

		url = url.substring(url.lastIndexOf("://") + 3); // strip off scheme
		url = url.substring(url.indexOf(contextPath) + contextPath.length());

		if (url.length() > 1 && url.charAt(0) == '/') {
			url = url.substring(1);
		}

		return url;
	}
}
