package com.app.startstop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages ={"com.app.startstop"})
@EntityScan(basePackages = {"com.bootnotification.app.entity","com.app.startstop.entity"})
@EnableJpaRepositories(basePackages = {"com.bootnotification.app.repository","com.app.startstop.repository"})
public class StartstopApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartstopApplication.class, args);
	}

}
