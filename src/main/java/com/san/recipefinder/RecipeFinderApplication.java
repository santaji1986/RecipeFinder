package com.san.recipefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class RecipeFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeFinderApplication.class, args);
	}

}
