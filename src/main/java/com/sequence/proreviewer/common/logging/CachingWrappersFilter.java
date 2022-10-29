package com.sequence.proreviewer.common.logging;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class CachingWrappersFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        HttpServletRequest filteredRequest = request;
        HttpServletResponse filteredResponse = response;

        if (!isAsyncDispatch(request)) {
            filteredRequest = new CachingRequestWrapper(request);
            filteredResponse = new CachingResponseWrapper(response);
        }

        filterChain.doFilter(filteredRequest, filteredResponse);
    }
}
