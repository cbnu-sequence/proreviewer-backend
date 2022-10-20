package com.sequence.proreviewer.common.logging;

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

    private String method;
    private String url;

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        if (isCachingRequestWrapper(request)) {
            this.method = request.getMethod();
            this.url = request.getRequestURI();
            String body = IOUtils.toString(
                request.getInputStream(),
                request.getCharacterEncoding()
            );
            log.info("request - {} {} ::: {}", method, url, body);
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
            String body = IOUtils.toString(
                ((CachingResponseWrapper) response).getContentInputStream(),
                response.getCharacterEncoding()
            );
            log.info("response - {} {} ::: {}", method, url, body);
        }
    }

    private boolean isCachingRequestWrapper(HttpServletRequest request) {
        return request instanceof CachingRequestWrapper;
    }

    private boolean isCachingResponseWrapper(HttpServletResponse response) {
        return response instanceof CachingResponseWrapper;
    }
}
