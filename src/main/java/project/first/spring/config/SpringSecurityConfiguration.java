package project.first.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().ignoringRequestMatchers("/sb/fc/**")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/sb/fc/**", "/api/v1/**").permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and().httpBasic();

        httpSecurity.headers().frameOptions().sameOrigin();
        return httpSecurity.build();
    }
}
