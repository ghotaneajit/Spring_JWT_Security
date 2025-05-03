package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.authentication.AuthenticationManager;

import com.example.demo.filter.AppFilter;
import com.example.demo.service.CustomerService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


	private final AppFilter jwtAuthFilter;
	private final CustomerService customerService;

    
    // Constructor injection for required dependencies
    @Autowired
    public AppSecurityConfig(AppFilter jwtAuthFilter, CustomerService customerService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.customerService = customerService;
    }

	
	
			@Bean
			public BCryptPasswordEncoder pwdEncoder() {
				
				return new BCryptPasswordEncoder();
			}
			
			
			@Bean
			public AuthenticationProvider authProvider() { 
					
				DaoAuthenticationProvider provider  = new DaoAuthenticationProvider();
				provider.setPasswordEncoder(pwdEncoder());
				provider.setUserDetailsService(customerService);
				
				return provider;
			}
	
			
			@Bean
		    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		        return config.getAuthenticationManager();
		    }
		
			
	
			@Bean
			public SecurityFilterChain security(HttpSecurity http) throws Exception {
				http
				.csrf(csrf -> csrf.disable())
				  .authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/customers/Register", "/api/customers/Login","/api/customers/generateToken").permitAll().
					anyRequest().authenticated()
					)
				  .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		            
		            // Set custom authentication provider
		            .authenticationProvider(authProvider())
		            
		            // Add JWT filter before Spring Security's default filter
		            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

				
				return http.build();
			}
	
}
