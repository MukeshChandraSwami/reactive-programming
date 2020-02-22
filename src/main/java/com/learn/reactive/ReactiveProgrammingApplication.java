package com.learn.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.learn")
public class ReactiveProgrammingApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReactiveProgrammingApplication.class, args);

		System.out.println("I am running fine");
	}
}
