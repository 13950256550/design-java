package com.aerodynamic.design.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
        .antMatchers("/static/**","/api/**","/service/**","/admin/**","/h2-console/**","/*").permitAll()
        .and()
        .headers().frameOptions().disable() //允许iframe
        .and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/manage/login")
		.defaultSuccessUrl("/")
		.failureUrl("/login-error")
		.permitAll() // 表单登录，permitAll()表示这个不需要验证
		.and()
		.logout()
		.logoutSuccessUrl("/")
		.and()
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.csrf()
		.disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
        .inMemoryAuthentication()
        .passwordEncoder(new DesignPasswordEncoder())//在此处应用自定义PasswordEncoder
        .withUser("user")
        .password("password")
        .roles("USER");
	}
	
}
