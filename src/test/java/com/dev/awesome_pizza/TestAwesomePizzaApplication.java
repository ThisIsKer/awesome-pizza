package com.dev.awesome_pizza;

import org.springframework.boot.SpringApplication;

public class TestAwesomePizzaApplication {

	public static void main(String[] args) {
		SpringApplication.from(AwesomePizzaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
