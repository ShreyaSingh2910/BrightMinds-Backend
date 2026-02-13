package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .headers(headers -> headers
    .crossOriginOpenerPolicy(coop ->
        coop.policy(org.springframework.security.web.header.writers.CrossOriginOpenerPolicyHeaderWriter.CrossOriginOpenerPolicy.UNSAFE_NONE)
    )
)
                .cors(Customizer.withDefaults())
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

                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}



