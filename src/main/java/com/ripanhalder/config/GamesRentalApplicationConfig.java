package com.ripanhalder.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*@Configuration shows that a class can declare one or more @Bean methods. 
		They might be processed using Spring container. It will generate service requests and bean definitions for such beans during the run time.
@EnableWebMvc enables Spring MVC configs to WebMvcConfigurationSupport.
@EnableTransactionManagement enables annotation-driven transaction management for Spring MVC
@ComponentScan scans the packages for all the components needed.
@PropertySource fetches the environment properties created. */

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages="com.ripanhalder")
@PropertySource("classpath:persistence-mysql.properties")
public class GamesRentalApplicationConfig {

	// Properties wiring to load in this page
	@Autowired
	private Environment env;
	
	// setting up a logger for all the checking
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	// This bean is used for creating ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		//Only these prefixed jsps will be used in the view resolver.
		viewResolver.setPrefix("/WEB-INF/view/");
		//Only pages with .jsp are allowed
		viewResolver.setSuffix(".jsp");
		//Returning the viewResolver object.
		return viewResolver;
	}
	
	// This bean is used for security datasource
	
	@Bean
	public DataSource dataSourceForSecurity() {
		
		// creating the connection pool for our data source
		ComboPooledDataSource dataSourceForSecurity = new ComboPooledDataSource();

		// set up jdbc driver for our data source
		try {
			dataSourceForSecurity.setDriverClass("com.mysql.jdbc.Driver");		
		}
		//PropertyVeto Exception occurs when an unacceptable value is entered in a property.
		catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}
		
		// logging the url and the user
		logger.info("jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info("jdbc.user=" + env.getProperty("jdbc.user"));
		
		// setting up database connection properties like url, user and password
		dataSourceForSecurity.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSourceForSecurity.setUser(env.getProperty("jdbc.user"));
		dataSourceForSecurity.setPassword(env.getProperty("jdbc.password"));
		
		// setting up all connection pool properties like initialPoolSize, minimum maximum pool sizes and maxIdleTime
		dataSourceForSecurity.setInitialPoolSize(
		environmentPropertyInINT("connection.pool.initialPoolSize"));

		dataSourceForSecurity.setMinPoolSize(
				environmentPropertyInINT("connection.pool.minPoolSize"));
		
		dataSourceForSecurity.setMaxPoolSize(
				environmentPropertyInINT("connection.pool.maxPoolSize"));
		
		dataSourceForSecurity.setMaxIdleTime(
				environmentPropertyInINT("connection.pool.maxIdleTime"));
		
		//Return the data source object
		return dataSourceForSecurity;
	}
	 
	//It reads the environment properties 
	//After reading the properties, it converts them into int
	
	private int environmentPropertyInINT(String propName) {
		
		//Read the prop
		String propVal = env.getProperty(propName);
		
		// and convert it to int
		int intPropVal = Integer.parseInt(propVal);
		
		//Return the converted prop value
		return intPropVal;
	}
	
	private Properties fetchAllHibernateProps() {

		// Create new hibernate property
		Properties props = new Properties();

		//Set up hibernate dialect
		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		//Show SQL on console
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		//On creation of session factory the hibernate automatically validates and exports schema to the mysql db
		props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		//Returning the property object
		return props;				
	}

	
	@Bean
	public LocalSessionFactoryBean sessionFactObject(){
		
		// create a session factory object
		LocalSessionFactoryBean sessionFactObject = new LocalSessionFactoryBean();
		
		// set the properties like DataSource, PackagesToScan and Hibernate props
		sessionFactObject.setDataSource(dataSourceForSecurity());
		sessionFactObject.setPackagesToScan(env.getProperty("hiberante.packagesToScan"));
		sessionFactObject.setHibernateProperties(fetchAllHibernateProps());
		
		//Return the session factory object
		return sessionFactObject;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager hbmtransactionManager(SessionFactory sessionFactObject) {
		//Create a new HibernateTransactionManager object
		HibernateTransactionManager hbmtransactionManager = new HibernateTransactionManager();
		// Setting hbm tx manager based on session factory
		hbmtransactionManager.setSessionFactory(sessionFactObject);
		//Return the HibernateTransactionManager object
		return hbmtransactionManager;
	}	
	
	@Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
         
        //I'm implemented the mail sender using GMAIL
        //Setting up the host
        mailSender.setHost("smtp.gmail.com");
        //Setting up the FROM Username
        mailSender.setUsername("gamesrentalfinalproject@gmail.com");
        //Setting up the FROM Password
        mailSender.setPassword("q1a2z3w4s5x6");
        //Setting up the default port to send emails 
        mailSender.setPort(587);
         
        Properties javaMailProperties = new Properties();
        // Setting up the protocol to smtp
        javaMailProperties.put("mail.transport.protocol", "smtp");
        // Enabling up the smtp suthentication
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        // This will print out everything on console
        javaMailProperties.put("mail.debug", "true");
         
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }


}
















