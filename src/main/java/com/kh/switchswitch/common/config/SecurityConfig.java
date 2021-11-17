package com.kh.switchswitch.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.kh.switchswitch.member.model.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	MemberService memberService;
	
	
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowSemicolon(true);  
	    return firewall;
	}
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/**","/switchswitch/resources/**", "/resources/**");
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
	
	//@Override
	//public void configure(AuthenticationManagerBuilder auth) throws Exception {
	//    auth.userDetailsService(memberService); 
	//}

}
