package project.first.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/sb/fc/**","/sb/cache/**").authenticated().and().httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers("/v3/api-docs**","/swagger-ui/**","/swagger-ui.html", "/api/v1/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();

/*        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint()));*/
        httpSecurity.headers().frameOptions().sameOrigin();

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/login"); // Redirect unauthorized requests to the authorization server's login page
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
