package com.bootnotification.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.bootnotification.app")
@EnableJpaRepositories(basePackages = "com.bootnotification.app.repository")
@EnableScheduling
public class BootNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootNotificationApplication.class, args);
	}

}
