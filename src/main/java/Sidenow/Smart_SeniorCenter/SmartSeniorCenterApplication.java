package Sidenow.Smart_SeniorCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartSeniorCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartSeniorCenterApplication.class, args);
	}

}
