package com.dauXanh.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.dauXanh.*")
@PropertySources(value = { @PropertySource("classpath:hibernate.properties") })
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Reading message.properties files for overriding Annotation messages
	 * 
	 * @return
	 */
	@Bean
	MessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
		bundleMessageSource.setBasenames("classpath:message");
		bundleMessageSource.setDefaultEncoding("utf-8");

		return bundleMessageSource;
	}

	/**
	 * ViewResolver: Preventive views
	 * 
	 * @return
	 */
	@Bean
	ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".jsp");
		// setOrder(_) to use other ViewResolvers at the same time.
		// In this situation, we combine InternalResourceViewResolver and
		// TilesViewResolver
		viewResolver.setOrder(2);

		return viewResolver;
	}

	/**
	 * Spring Tiles 3: Majority views
	 * 
	 * @return
	 */
	@Bean
	TilesConfigurer tilesConfigurer() {
	
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("classpath:tiles.xml");
		tilesConfigurer.setCheckRefresh(true);

		return tilesConfigurer;
	}

	@Bean
	ViewResolver tilesViewResolver() {
	
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		// setOrder(_) to use other ViewResolvers at the same time.
		// In this situation, we combine InternalResourceViewResolver and
		// TilesViewResolver
		tilesViewResolver.setOrder(1);

		return tilesViewResolver;
	}

	/**
	 * Static Resources: css files, js files, imgs, third-party libraries, etc
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		// Project files
		registry.addResourceHandler("/public/**").addResourceLocations("/public/");

		// System files (computer files)
		registry.addResourceHandler("/server/**").addResourceLocations("file:C:/Users/Admin/Desktop/online_website/");
	}

	/**
	 * Uploading file
	 */
	@Bean
	public MultipartResolver multipartResolver() {
	
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		// -1 means unlimited file size
		multipartResolver.setMaxUploadSize(-1);

		return multipartResolver;
	}

	/**
	 * Database connecting
	 */
	// Data Holder: holding data read from properties files
	@Autowired
	Environment env;

	// Reading database.properties file from @PropertySource for connecting DB
	@Bean
	static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
	
		return new PropertySourcesPlaceholderConfigurer();
	}

	// Data source
	@Bean
	DataSource dataSource() {
	
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

//		// By hard code
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/web_student_tracker");
//		dataSource.setUsername("webstudent");
//		dataSource.setPassword("webstudent");

		// By reading properties file
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));

		return dataSource;
	}

	// Hibernate properties
	@Bean
	Properties hibernateProperties() {
	
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		// So important: It allow us to use Lazy Fetching
		properties.put("hibernate.enable_lazy_load_no_trans",
				env.getRequiredProperty("hibernate.enable_lazy_load_no_trans"));
		properties.put("hibernate.event.merge.entity_copy_observer",
				env.getRequiredProperty("hibernate.event.merge.entity_copy_observer"));

		return properties;
	}

	// Hibernate session factory
	@Bean
	LocalSessionFactoryBean sessionFactory(@Autowired Properties hibernateProperties,
			@Autowired DataSource dataSource) {
	
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(env.getRequiredProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(hibernateProperties);

		return sessionFactory;
	}

	// Spring transaction
	@Bean
	HibernateTransactionManager transactionManager(@Autowired SessionFactory sessionFactory) {
	
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);

		return transactionManager;
	}

	/**
	 * Spring Mail
	 */
	final String SENDER_USERNAME = "senderEmail@gmail.com";
	final String SENDER_PASSWORD = "senderPassword";

	@Bean
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(SENDER_USERNAME);
		mailSender.setPassword(SENDER_PASSWORD);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	/**
	 * Interceptor: Stop this module temporary
	 * 
	 * @return
	 */
//	@Bean
//	public FilterSystem filterSystemInterceptor() {
//
//		return new FilterSystem();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//
//		registry.addInterceptor(filterSystemInterceptor()).addPathPatterns("/**")
//				// > Bo cac endpoints nay khong can preHandle
//				.excludePathPatterns("/login")
//				.excludePathPatterns("/resources/**")
//				.excludePathPatterns("/upload/**");
//	}
}
