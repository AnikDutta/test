package demo.cosmos.core.aircraft.api;

import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:log.messages")
public class ApplicationConfiguration {
	
	@Autowired
	Environment environment;

	@Bean
	public ServletRegistrationBean camelServletRegistrationBean(@Value("${service.name}") String serviceName,
			@Value("${service.version}") String serviceVersion) {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(),
				String.format("/%s/%s/*", serviceName, serviceVersion));
		registration.setName("CamelServlet");
		return registration;
	}

	@Bean
	CamelContextConfiguration contextConfiguration(@Value("${service.name}") String name) {
		return new CamelContextConfiguration() {
			@Override
			public void beforeApplicationStart(CamelContext context) {
				((SpringCamelContext) context).setName(name);
			}

			@Override
			public void afterApplicationStart(CamelContext context) {
			}
		};
	}

	@Bean
	public ValidatorFactory validatorFactory() {
		return Validation.buildDefaultValidatorFactory();
	}

	@Bean
	public Validator validator() {
		return validatorFactory().getValidator();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean(name = "dataSource")
	@ConditionalOnExpression("#{environment.getProperty('service.type').equals('core')}")
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(environment.getProperty("datasource.url"));
		config.setUsername(environment.getProperty("datasource.username"));
		config.setPassword(environment.getProperty("datasource.password"));
		config.setDriverClassName(environment.getProperty("datasource.driver-class-name"));
		config.setMaximumPoolSize(Integer.parseInt(environment.getProperty("hikari.max.pool.size")));
		config.setConnectionTimeout(Long.parseLong(environment.getProperty("hikari.connection.timeout")));
		config.setMaxLifetime(Long.parseLong(environment.getProperty("hikari.max.lifetime")));
		DataSource datasource = new HikariDataSource(config);
		return datasource;
	}
	

}
