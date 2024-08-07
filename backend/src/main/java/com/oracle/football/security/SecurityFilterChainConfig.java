package com.oracle.football.security;

import com.oracle.football.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationEntryPoint authenticationEntryPoint;

  public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
      JwtAuthenticationFilter jwtAuthenticationFilter,
      @Qualifier("delegatedAuthEntryPoint") AuthenticationEntryPoint authenticationEntryPoint) {
    this.authenticationProvider = authenticationProvider;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(auth -> auth.
            requestMatchers(
                HttpMethod.POST,
                "/api/v1/auth/login",
                "/api/v1/users"
            )
            .permitAll()
            .requestMatchers(
                HttpMethod.GET,
                "/ping",
                "/api/v1/users"
            )
            .permitAll()
            .requestMatchers(
                HttpMethod.GET,
                "/actuator/**")
            .permitAll()
            .requestMatchers(
                "v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            )
            .permitAll()
            .anyRequest()
            .authenticated()
        )
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(
            jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        )
        .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint));

    return http.build();
  }

}
