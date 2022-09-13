package com.sequence.proreviewer.util.interceptor.logging;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class CachingWrappersFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		if (!isAsyncDispatch(request)) {
			request = new CachingRequestWrapper(request);
			response = new CachingResponseWrapper(response);
		}
		filterChain.doFilter(request, response);
	}
}
