/**
 * this is the starter class, which used for start the project and attach it into the web server.
 */
package com.fasimito.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
@SpringBootApplication(scanBasePackages="com.fasimito.*")
public class Main extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Main.class);
	}
	
	public static void main(String[] args) throws Exception{
		SpringApplication.run(Main.class, args);
	}

}
