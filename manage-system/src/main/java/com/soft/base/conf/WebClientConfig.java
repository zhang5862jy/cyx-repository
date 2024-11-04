package com.soft.base.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

/**
 * @Author: 程益祥
 * @Description: TODO
 * @DateTime: 2024/11/4 14:01
 **/

@Configuration
public class WebClientConfig {

    @Bean
    public Builder webClient() {
        return WebClient.builder();
    }
}
