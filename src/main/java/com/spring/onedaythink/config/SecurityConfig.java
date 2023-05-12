package com.spring.onedaythink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final JwtTokenProvider jwtTokenProvider;
//
//    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // CORS 설정
                    .and()
                .csrf().disable() // CSRF 설정 비활성화
                .sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                    .and()
                .authorizeRequests()
//                    .antMatchers("/api/v1/login").permitAll() // 인증없이 접근 가능한 URL 패턴
//                    .antMatchers("/api/vi/**").authenticated()
//                .and()
//                .logout()
//                    .logoutUrl("/api/v1/auth/logout") // 로그아웃 요청 URL
//                    .logoutSuccessUrl("/")
//                    .permitAll();
                    .antMatchers("/flask/v1/**").permitAll()
                    .antMatchers("/api/v1/**").permitAll()
                    .antMatchers("/ws/**").permitAll() //
                    .antMatchers("/stomp/ws/**").permitAll() // 모든 프리플라이트 요청 허용
                .anyRequest().authenticated();
//                .and()
//                .apply(new JwtConfig(jwtTokenProvider)); // JwtConfig 적용
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("foo").password("{noop}bar").roles("USER");
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4000", "http://localhost:5000")); // 클라이언트의 도메인
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE")); // 허용할 HTTP 메소드
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "RefreshToken", "Content-Type")); // JWT 토큰과 Content-Type 허용
        configuration.setExposedHeaders(Arrays.asList("Authorization", "RefreshToken")); // JWT 토큰 노출 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}