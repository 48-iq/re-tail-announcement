package dev.ilya_anna.announcement_service.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.ilya_anna.announcement_service.security.DaoAnnouncementAuthorizer;
import dev.ilya_anna.announcement_service.security.DaoAnnouncementImageAuthorizer;
import dev.ilya_anna.announcement_service.security.JwtFilter;

@Configuration
public class SecurityConfig {
  
  @Autowired
  private JwtFilter jwtFilter;

  @Autowired
  private DaoAnnouncementAuthorizer daoAnnouncementAuthorizer;

  @Autowired
  private DaoAnnouncementImageAuthorizer daoAnnouncementImageAuthorizer;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(request -> request
              .requestMatchers(HttpMethod.GET).permitAll()
              .requestMatchers(HttpMethod.POST, "/api/v1/announcements/{id}/images").access(daoAnnouncementImageAuthorizer)
              .requestMatchers("/api/v1/announcements/**").access(daoAnnouncementAuthorizer)
              .anyRequest().permitAll()
      )
      .anonymous(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    .build();
  }
}
