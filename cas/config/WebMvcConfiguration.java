package com.itechro.cas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	private static final Logger LOG = LoggerFactory.getLogger(WebMvcConfiguration.class);

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/ui").setViewName("forward:/ui/index.html");
		registry.addViewController("/ui/").setViewName("forward:/ui/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
}
