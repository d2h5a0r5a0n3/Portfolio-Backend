package com.d2h5a0r5a0n3.portfolio.configuration;

import com.d2h5a0r5a0n3.portfolio.util.UrlApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@Slf4j
public class SecurityConfig {
    @Value("${admin.password}")
    private String adminPassword;

    @Value("${api.frontend.url}")
    public String apiFrontend;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails adminUser = User.builder()
                .username("Admin")
                .password(encoder.encode(adminPassword))
                .roles(UrlApiUtil.API_ADMIN)
                .build();

        return new InMemoryUserDetailsManager(adminUser);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/resumes","/api/resumes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/resumes/upload").permitAll()
                        .requestMatchers(HttpMethod.POST, UrlApiUtil.API_CONTACT).permitAll()
                        .requestMatchers(HttpMethod.GET, UrlApiUtil.API_PROJECT, UrlApiUtil.API_PROJECTS).permitAll()
                        .requestMatchers(HttpMethod.GET, UrlApiUtil.API_EDUCATION, UrlApiUtil.API_EDUCATIONS).permitAll()
                        .requestMatchers(HttpMethod.GET, UrlApiUtil.API_EXPERIENCE, UrlApiUtil.API_EXPERIENCES).permitAll()
                        .requestMatchers(HttpMethod.POST, UrlApiUtil.API_LOGIN, UrlApiUtil.API_LOGOUT).permitAll()
                        .requestMatchers(HttpMethod.GET, UrlApiUtil.API_SESSION_STATUS, UrlApiUtil.API_IS_ADMIN).permitAll()
                        .requestMatchers(HttpMethod.POST, UrlApiUtil.API_PROJECT, UrlApiUtil.API_EXPERIENCE, UrlApiUtil.API_EDUCATION).hasRole(UrlApiUtil.API_ADMIN)
                        .requestMatchers(HttpMethod.PUT, UrlApiUtil.API_PROJECTS, UrlApiUtil.API_EXPERIENCES, UrlApiUtil.API_EDUCATIONS).hasRole(UrlApiUtil.API_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, UrlApiUtil.API_PROJECTS, UrlApiUtil.API_EXPERIENCES, UrlApiUtil.API_EDUCATIONS).hasRole(UrlApiUtil.API_ADMIN)
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
