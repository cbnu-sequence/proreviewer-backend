package com.sequence.proreviewer.common.config;

import com.sequence.proreviewer.common.logging.HttpLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final HttpLoggingInterceptor httpLoggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(httpLoggingInterceptor)
			.excludePathPatterns("**/");
	}
}
