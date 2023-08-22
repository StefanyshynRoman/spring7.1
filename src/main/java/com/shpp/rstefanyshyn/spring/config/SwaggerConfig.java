package com.shpp.rstefanyshyn.spring.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Slf4j
@Configuration
public class SwaggerConfig {
    @Value("${api.ver}")
    private String version;
    @Value("${api.description}")
    private String description;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API")
                        .description(description)
                        .termsOfService("http://restapi.com")
                        .version(version)

                );

    }
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("JavaInUse API")
//                .description("JavaInUse API reference for developers")
//                .termsOfServiceUrl("http://javainuse.com")
//                .contact("javainuse@gmail.com").license("JavaInUse License")
//                .licenseUrl("javainuse@gmail.com").version("1.0").build();
//    }
}