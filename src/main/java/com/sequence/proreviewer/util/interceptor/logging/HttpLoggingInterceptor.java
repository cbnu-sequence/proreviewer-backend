package com.sequence.proreviewer.util.interceptor.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

	private final ObjectMapper objectMapper;

	public HttpLoggingInterceptor(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception {
		if (isCachingRequestWrapper(request)) {
			String req = IOUtils.toString(
				request.getInputStream(),
				request.getCharacterEncoding()
			);
			log.info("request - {}", req);
		}
		return true;
	}

	@Override
	public void afterCompletion(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		@Nullable Exception ex
	) throws Exception {
		if (isCachingResponseWrapper(response)) {
			String res = IOUtils.toString(
				((CachingResponseWrapper) response).getContentInputStream(),
				response.getCharacterEncoding()
			);
			log.info("response - {}", res);
		}
	}

	private boolean isCachingRequestWrapper(HttpServletRequest request) {
		return request instanceof CachingRequestWrapper;
	}

	private boolean isCachingResponseWrapper(HttpServletResponse response) {
		return response instanceof CachingResponseWrapper;
	}
}
