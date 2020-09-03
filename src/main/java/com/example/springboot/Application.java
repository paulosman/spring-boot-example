package com.example.springboot;

import java.util.Arrays;

import io.honeycomb.beeline.builder.BeelineBuilder;
import io.honeycomb.beeline.spring.beans.DebugResponseObserver;
import io.honeycomb.beeline.tracing.Beeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private Environment env;

	@Bean
	public Beeline getBeeline() {
	    final String dataset = env.getProperty("honeycomb.beeline.dataset");
	    final String writeKey = env.getProperty("honeycomb.beeline.write-key");
		final BeelineBuilder builder = new BeelineBuilder();
		builder.dataSet(dataset).writeKey(writeKey).addProxy("http://localhost:4444");
		return builder.build();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}
