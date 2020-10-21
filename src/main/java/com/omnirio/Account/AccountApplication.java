package com.omnirio.Account;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

import com.omnirio.Account.filter.JwtRequestFilter;

@EnableEurekaClient
@SpringBootApplication
public class AccountApplication {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public JwtRequestFilter getJwtRequestFilter() {
		return new JwtRequestFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}
