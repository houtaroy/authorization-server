package cn.houtaroy.authorization.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 默认 Spring Security 配置类
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/assets/**", "/login").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                );

        return http.build();
    }
}
