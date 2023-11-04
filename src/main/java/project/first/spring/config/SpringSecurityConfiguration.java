package project.first.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/v3/api-docs**","/swagger-ui/**","/swagger-ui.html")
                .permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer().jwt();
        return httpSecurity.build();
    }
}
