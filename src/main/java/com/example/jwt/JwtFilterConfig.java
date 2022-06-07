package com.example.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

	@Autowired
	private JwtService jwtService;

	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter() {
		var registrationBean = new FilterRegistrationBean<JwtFilter>();

		registrationBean.setFilter(new JwtFilter(jwtService));
		registrationBean.addUrlPatterns("/api/time");

		return registrationBean;
	}

}
