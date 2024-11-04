package com.soft.base.conf;

import com.soft.base.filter.JwtRequestFilter;
import com.soft.base.handle.AuthenticationHandler;
import com.soft.base.handle.LogoutAfterSuccessHandler;
import com.soft.base.properties.JwtIgnoreProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    private final AuthenticationHandler authenticationHandler;

    private final LogoutAfterSuccessHandler logoutAfterSuccessHandler;

    private final UserDetailsService userDetailsService;

    private JwtIgnoreProperty jwtIgnoreProperty;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter,
                          AuthenticationHandler authenticationHandler,
                          LogoutAfterSuccessHandler logoutAfterSuccessHandler,
                          UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationHandler = authenticationHandler;
        this.logoutAfterSuccessHandler = logoutAfterSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setPermits(JwtIgnoreProperty jwtIgnoreProperty) {
        this.jwtIgnoreProperty = jwtIgnoreProperty;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth
                            // 需要放行的接口
                            .requestMatchers(jwtIgnoreProperty.getUrls()).permitAll()
                            .requestMatchers("/webjars/**", "/v3/**").permitAll()
                            // 其它接口都需要校验
                            .anyRequest().authenticated();
                })
                //禁用HTTP响应标头
                .headers((headersCustomizer) -> {
                    headersCustomizer.cacheControl(cache -> cache.disable()).frameOptions(options -> options.sameOrigin());
                })
                // 认证失败处理类
                .exceptionHandling(exc ->
                                exc
                                        // 用于处理未认证的请求（如未登录用户访问受保护的资源）
                                        .authenticationEntryPoint(authenticationHandler)
                        // 用于处理已认证但没有权限访问资源的请求
                        //.accessDeniedHandler()
                )
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .logout(item -> item.logoutUrl("/logout")
                        .logoutSuccessHandler(logoutAfterSuccessHandler));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
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

//    /**
//     * AuthenticationManager 手动注入
//     *
//     * @param authenticationConfiguration 认证配置
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}
