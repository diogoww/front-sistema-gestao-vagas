package br.com.diogow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> {
				auth.requestMatchers("/candidate/login").permitAll()
						.requestMatchers("/candidate/signIn").permitAll()
						.requestMatchers("/candidate/create").permitAll()
						.requestMatchers("/company/create").permitAll()
						.requestMatchers("/company/login").permitAll()
						.requestMatchers("/company/signIn").permitAll();

						auth.anyRequest().authenticated();
			})
			.formLogin(form -> form
					.loginPage("/candidate/login")
					.defaultSuccessUrl("/candidate/jobs", true)
					.permitAll())
			.csrf(csrf -> csrf.disable());
		return http.build();
	}
}
