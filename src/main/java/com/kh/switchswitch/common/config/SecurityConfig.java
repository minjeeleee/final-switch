package com.kh.switchswitch.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.mvcMatchers("/resources/**");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.mvcMatchers(HttpMethod.GET,"/notice/noticeList","/member/logout").authenticated()
		.anyRequest().permitAll();
	
	http.formLogin()
		.loginProcessingUrl("/member/login")
		.usernameParameter("email")
		.loginPage("/member/login")
		.defaultSuccessUrl("/");
	
	http.logout()
	.logoutUrl("/member/logout")
	.logoutSuccessUrl("/member/login");
	
	
	http.csrf().ignoringAntMatchers("/mail");
	}

}
