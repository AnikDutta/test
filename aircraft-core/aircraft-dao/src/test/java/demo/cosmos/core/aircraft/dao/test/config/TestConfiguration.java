package demo.cosmos.core.aircraft.dao.test.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages = "demo.cosmos.core.aircraft.dao.*")
public class TestConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate() {
	return Mockito.mock(JdbcTemplate.class);
    }

}
