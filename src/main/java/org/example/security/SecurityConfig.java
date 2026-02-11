package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // ✅ allow all static frontend files
                        .requestMatchers(
                                "/",
                                "/*.html",
                                "/*.css",
                                "/*.js",
                                "/assets/**",
                                "/avatars/**",
                                "Leaderboard/**",
                                "/FractionBuilder/**",
                                "/MatchGame/**",
                                "/MemoryGame/**",
                                "/ScienceSpotter/**",
                                "/WordBuilder/**"
                        ).permitAll()

                        // ✅ allow API for now
                        .requestMatchers("/api/**").permitAll()

                        // everything else
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}

