package com.soft.base.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("接口文档") // 修改为你想要的标题
                        .version("1.0.0") // 版本
                        .description("API Description").contact(new Contact()
                                .name("程益祥")
                                .email("1574641450@qq.com")));
    }
}
