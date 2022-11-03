package com.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/bookstore/**")
			.hasAnyRole("ADMIN","USER")
			.antMatchers(HttpMethod.POST,"/bookstore/**")
			.hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT,"/bookstore/**")
			.hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE,"/bookstore/**")
			.hasRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();

		/*http.authorizeRequests()
		.antMatchers("/bookstore/book/**")
		.hasRole("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();*/
	}

	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}


}
