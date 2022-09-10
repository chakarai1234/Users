package com.chakarapani.user;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EntityScan("com.chakarapani.base")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${application.version}") String applicationVersion) {
		return new OpenAPI().info(
				new Info().title("Users Microservice").description("Micro service that handles " + "users information")
						.version(applicationVersion)
						.contact(new Contact().name("Srinivasan Chakarapani").email("developer@email.com")));
	}

}
