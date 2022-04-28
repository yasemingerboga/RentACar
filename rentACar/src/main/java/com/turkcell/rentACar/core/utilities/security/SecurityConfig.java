package com.turkcell.rentACar.core.utilities.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.turkcell.rentACar.core.utilities.filter.CustomAuthenticationFilter;
import com.turkcell.rentACar.core.utilities.filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private static final String[] AUTH_WHITELIST = { "/v3/api-docs/**", "/swagger-ui/**" };

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
				authenticationManagerBean());

		customAuthenticationFilter.setFilterProcessesUrl("/api/login/**");

		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
		http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();

		http.authorizeRequests().antMatchers("GET", "/api/user/**").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers("GET", "/api/rentalCars/**").hasAnyAuthority("ROLE_USER");
		http.authorizeRequests().antMatchers("POST", "/api/cars/add/**").hasAnyAuthority("ROLE_USER");
		http.authorizeRequests().antMatchers("POST", "/api/rentalCars/update/**").hasAnyAuthority("ROLE_ADMIN");

		http.authorizeRequests().anyRequest().authenticated();

		http.addFilter(customAuthenticationFilter);

		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
