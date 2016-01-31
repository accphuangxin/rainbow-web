package org.rainbow.web.common;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public final class UrlUtils {
	// ~ Methods
	// ========================================================================================================

	public static String buildFullRequestUrl(HttpServletRequest r) {
		return buildFullRequestUrl(r.getScheme(), r.getServerName(), r.getServerPort(),
				r.getRequestURI(), r.getQueryString());
	}

	/**
	 * Obtains the full URL the client used to make the request.
	 * <p>
	 * Note that the server port will not be shown if it is the default server port for
	 * HTTP or HTTPS (80 and 443 respectively).
	 *
	 * @return the full URL, suitable for redirects (not decoded).
	 */
	public static String buildFullRequestUrl(String scheme, String serverName,
			int serverPort, String requestURI, String queryString) {

		scheme = scheme.toLowerCase();

		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		// Only add port if not default
		if ("http".equals(scheme)) {
			if (serverPort != 80) {
				url.append(":").append(serverPort);
			}
		}
		else if ("https".equals(scheme)) {
			if (serverPort != 443) {
				url.append(":").append(serverPort);
			}
		}

		// Use the requestURI as it is encoded (RFC 3986) and hence suitable for
		// redirects.
		url.append(requestURI);

		if (queryString != null) {
			url.append("?").append(queryString);
		}

		return url.toString();
	}

	/**
	 * Obtains the web application-specific fragment of the request URL.
	 * <p>
	 * Under normal spec conditions,
	 * 
	 * <pre>
	 * requestURI = contextPath + servletPath + pathInfo
	 * </pre>
	 *
	 * But the requestURI is not decoded, whereas the servletPath and pathInfo are
	 * (SEC-1255). This method is typically used to return a URL for matching against
	 * secured paths, hence the decoded form is used in preference to the requestURI for
	 * building the returned value. But this method may also be called using dummy request
	 * objects which just have the requestURI and contextPatth set, for example, so it
	 * will fall back to using those.
	 *
	 * @return the decoded URL, excluding any server name, context path or servlet path
	 *
	 */
	public static String buildRequestUrl(HttpServletRequest r) {
		return buildRequestUrl(r.getServletPath(), r.getRequestURI(), r.getContextPath(),
				r.getPathInfo(), r.getQueryString());
	}

	/**
	 * Obtains the web application-specific fragment of the URL.
	 */
	private static String buildRequestUrl(String servletPath, String requestURI,
			String contextPath, String pathInfo, String queryString) {

		StringBuilder url = new StringBuilder();

		if (servletPath != null) {
			url.append(servletPath);
			if (pathInfo != null) {
				url.append(pathInfo);
			}
		}
		else {
			url.append(requestURI.substring(contextPath.length()));
		}

		if (queryString != null) {
			url.append("?").append(queryString);
		}

		return url.toString();
	}

	/**
	 * Returns true if the supplied URL starts with a "/" or is absolute.
	 */
	public static boolean isValidRedirectUrl(String url) {
		return url != null && url.startsWith("/") || isAbsoluteUrl(url);
	}

	/**
	 * Decides if a URL is absolute based on whether it contains a valid scheme name, as
	 * defined in RFC 1738.
	 */
	public static boolean isAbsoluteUrl(String url) {
		final Pattern ABSOLUTE_URL = Pattern.compile("\\A[a-z0-9.+-]+://.*",
				Pattern.CASE_INSENSITIVE);

		return ABSOLUTE_URL.matcher(url).matches();
	}
}