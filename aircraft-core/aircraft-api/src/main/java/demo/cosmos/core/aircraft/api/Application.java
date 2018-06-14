package demo.cosmos.core.aircraft.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cognizant.cosmos, demo.cosmos.core.aircraft")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
