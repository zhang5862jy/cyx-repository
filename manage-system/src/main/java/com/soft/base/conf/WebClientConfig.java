package com.soft.base.conf;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * @Author: cyx
 * @Description: http请求配置
 * @DateTime: 2024/11/4 14:01
 **/

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
