package com.example.spring1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.spring1.security.JwtAuthenticationEntryPoint;
import com.example.spring1.security.JwtAuthenticationFilter;
import com.example.spring1.services.UserDetailsServiceImpl;

// Tüm işlerin otomatik olarak sağlanması için bir configürasyona ihtiyacımız olacak.

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsServiceImpl   userDetailsService;

	private JwtAuthenticationEntryPoint handler;

	public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationEntryPoint handler) {
		this.userDetailsService = userDetailsService;
		this.handler = handler;
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return  new JwtAuthenticationFilter();
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	// Farklı adresten , porttan (origin ) gelen isteklerde sorun yaşamamak için bir önlem
	   @Bean
	    public CorsFilter corsFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("OPTIONS");
	        config.addAllowedMethod("HEAD");
	        config.addAllowedMethod("GET");
	        config.addAllowedMethod("PUT");	
	        config.addAllowedMethod("POST");
	        config.addAllowedMethod("DELETE");
	        config.addAllowedMethod("PATCH");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }
	   
	   @Override
	    public void configure(HttpSecurity httpSecurity) throws Exception {
	    	httpSecurity
	    		.cors()
	    		.and()
	    		.csrf().disable()
	    		.exceptionHandling().authenticationEntryPoint(handler).and()
	    		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	    		.authorizeRequests()
	    		.antMatchers(HttpMethod.GET, "/posts")
	    		.permitAll()
	    		.antMatchers(HttpMethod.GET,"/comments")
	    		.permitAll()
	    		.antMatchers("/auth/**")
	    		.permitAll()
	    		.anyRequest().authenticated();
	    		
	    	httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
	}
	

