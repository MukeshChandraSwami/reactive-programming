package com.learn.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@ComponentScan("com.learn")
@EnableMongoAuditing
public class ReactiveProgrammingApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReactiveProgrammingApplication.class, args);
		System.out.println("I am reactive data provider application and i am running fine");
	}
}
