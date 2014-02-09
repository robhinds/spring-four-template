package com.tmm.frm.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.js.ajax.AjaxUrlBasedViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;

@Configuration
@EnableWebMvc
@ComponentScan({"com.tmm.frm.controller", "com.tmm.frm.controller.api"})
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/webfonts/**").addResourceLocations("/webfonts/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/sign-in").setViewName("signin");
		registry.addViewController("/uncaughtException");
		registry.addViewController("/resourceNotFound");
		registry.addViewController("/dataAccessFailure");
		registry.addViewController("/accessDenied");
	}

	@Bean
	public DefaultAnnotationHandlerMapping defaultAnnotationHandlerMapping() {
		DefaultAnnotationHandlerMapping bean = new DefaultAnnotationHandlerMapping();
		bean.setAlwaysUseFullPath(true);
		return bean;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer bean = new TilesConfigurer();
		String[] definitions = { "/WEB-INF/layouts/layouts.xml", "/WEB-INF/views/**/views.xml" };
		bean.setDefinitions(definitions);
		return bean;
	}

	@Bean
	public AjaxUrlBasedViewResolver tilesViewResolver() {
		AjaxUrlBasedViewResolver bean = new AjaxUrlBasedViewResolver();
		bean.setViewClass(TilesView.class);
		return bean;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver bean = new SimpleMappingExceptionResolver();
		bean.setDefaultErrorView("uncaughtException");
		Properties mappings = new Properties();
		mappings.put(".DataAccessException", "dataAccessFailure");
		mappings.put(".NoSuchRequestHandlingMethodException", "resourceNotFound");
		mappings.put(".TypeMismatchException", "resourceNotFound");
		mappings.put(".MissingServletRequestParameterException", "resourceNotFound");
		mappings.put(".AccessDeniedException", "accessDenied");
		bean.setExceptionMappings(mappings);
		return bean;
	}
}