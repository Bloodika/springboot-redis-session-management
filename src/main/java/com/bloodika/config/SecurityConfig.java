package com.bloodika.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    
    private final AuthSuccessHandler authSuccessHandler;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LettuceConnectionFactory redisStandAloneConnectionFactory() {
    	 return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        final InMemoryUserDetailsManager reqDetailsManager = new InMemoryUserDetailsManager();
        reqDetailsManager.createUser(User.withUsername("bloodika").password(passwordEncoder().encode("aaa")).roles("USER").build());
        reqDetailsManager.createUser(User.withUsername("bloodika2").password(passwordEncoder().encode("aaaa")).roles("USER").build());
        return reqDetailsManager;
    }

    public SecurityConfig(AuthSuccessHandler authSuccessHandler) {
        this.authSuccessHandler = authSuccessHandler;
    }


    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
        .requestMatchers("/api/**")
        .authenticated()
        .requestMatchers("/**")
        .permitAll().and().formLogin()
        .successHandler(authSuccessHandler)
        .failureUrl("/error");
        return http.build();

    }

}
