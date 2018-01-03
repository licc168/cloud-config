package com.licc.dove;

import com.licc.dove.config.SecurityConfig;
import com.licc.dove.config.SessionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.licc.dove.config.MvcConfig;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@Import({
		MvcConfig.class,
		SessionConfig.class,
		SecurityConfig.class

})
@ComponentScan("com.licc")
public class DoveAdminConfigApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DoveAdminConfigApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DoveAdminConfigApplication.class, args);
	}

}
