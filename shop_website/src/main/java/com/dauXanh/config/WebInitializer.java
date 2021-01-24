package com.dauXanh.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext appContext = 
				new AnnotationConfigWebApplicationContext();
		appContext.register(WebConfig.class);
		appContext.setServletContext(servletContext);

		// DispatcherServlet: The front controller of this Spring Web application, 
		//	has responsible for handling all application requests
		DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
 
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("SpringDispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		dispatcher.setInitParameter("contextClass", appContext.getClass().getName());

		servletContext.addListener(new ContextLoaderListener(appContext));

		// RequestMapping: Map all requests to the DispatcherServlet for handling
		// UTF8 Character Filter
		FilterRegistration.Dynamic fr = 
				servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		fr.setInitParameter("encoding", "UTF-8");
		fr.setInitParameter("forceEncoding", "true");
		fr.addMappingForUrlPatterns(null, true, "/*");
	}
}

//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//public class WebInitializer 
//			extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//	@Override
//	protected Class<?>[] getRootConfigClasses() {
//		return null;
//	}
//
//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		return new Class[] { WebConfig.class };
//	}
//
//	@Override
//	protected String[] getServletMappings() {
//		return new String[] { "/" }; 
//	}
//}
 
