package com.its.issuetrackingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@PropertySource("classpath:application-bucket.properties")
public class IssueTrackingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueTrackingServiceApplication.class, args);
	}

}
