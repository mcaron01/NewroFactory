package com.oxyl.NewroFactory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.oxyl.NewroFactory.service.UserService;
import com.oxyl.NewroFactory.utils.JwtAuthenticationEntryPoint;
import com.oxyl.NewroFactory.utils.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public SecurityConfiguration(UserService userService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
		this.userService = userService;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtRequestFilter = jwtRequestFilter;
	}
	
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}


	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	

				http.cors()
			    .and().csrf().disable().authorizeRequests()
				.mvcMatchers("/login", "/register","/logout","/questions/**").permitAll()
				.mvcMatchers("/dashboard","/answer/**","/chapter","/chapter/*","/chapterDetails/*").hasAnyRole("USER", "ADMIN")
				.mvcMatchers("/addStagiaire", "/editStagiaire/*","/delete/*","/user/**").hasRole("ADMIN")
				.anyRequest()
				.authenticated()
				.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().rememberMe().rememberMeCookieName("remember-cookie").rememberMeParameter("remember-me").tokenValiditySeconds(3000)
				.and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
