package com.oxyl.NewroFactory.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(PersistenceConfiguration.class, ServiceConfiguration.class, MapperConfiguration.class,
				DtoConfiguration.class, DatabaseConfiguration.class, WebConfiguration.class,
				ValidationConfiguration.class, ExceptionConfiguration.class, SecurityConfiguration.class);
		
		servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
        .addMappingForUrlPatterns(null, false, "/*");
        
		DispatcherServlet servlet = new DispatcherServlet(context);
		ServletRegistration.Dynamic registration = servletContext.addServlet("NewroFactory", servlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
		servletContext.addFilter("characterEncoding", new CharacterEncodingFilter("UTF-8"))
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

	} 
}
