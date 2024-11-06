package com.soft.base.conf;

import com.soft.base.filter.JwtRequestFilter;
import com.soft.base.handle.AuthenticationHandler;
import com.soft.base.handle.LogoutAfterSuccessHandler;
import com.soft.base.properties.JwtIgnoreProperty;
import com.soft.base.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationHandler authenticationHandler;

    private final LogoutAfterSuccessHandler logoutAfterSuccessHandler;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final RedisTemplate<String,String> redisTemplate;

    private JwtIgnoreProperty jwtIgnoreProperty;

    @Autowired
    public SecurityConfig(AuthenticationHandler authenticationHandler,
                          LogoutAfterSuccessHandler logoutAfterSuccessHandler,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          RedisTemplate<String,String> redisTemplate) {
        this.authenticationHandler = authenticationHandler;
        this.logoutAfterSuccessHandler = logoutAfterSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setPermits(JwtIgnoreProperty jwtIgnoreProperty) {
        this.jwtIgnoreProperty = jwtIgnoreProperty;
    }

    private JwtRequestFilter getJwtRequestFilter() {
        return new JwtRequestFilter(jwtUtil,userDetailsService,redisTemplate);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用csrf
                .csrf(CsrfConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(jwtIgnoreProperty.toArray()).permitAll()
                            .anyRequest().authenticated();
                })
                .logout(item -> item.logoutUrl("/logout")
                        .logoutSuccessHandler(logoutAfterSuccessHandler))
                // 认证失败处理类
                .exceptionHandling(exc ->
                                exc
                                        // 用于处理未认证的请求（如未登录用户访问受保护的资源）
                                        .authenticationEntryPoint(authenticationHandler)
                        // 用于处理已认证但没有权限访问资源的请求
                        //.accessDeniedHandler()
                )
                .addFilterBefore(getJwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 强哈希算法 BCrypt 进行密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/webjars/**", "/v3/**");
    }
}
