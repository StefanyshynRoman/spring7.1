package com.shpp.rstefanyshyn.spring.config;


import io.swagger.annotations.AuthorizationScope;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;




@Slf4j
@Configuration
//@SecurityScheme(
//        type = SecuritySchemeType.HTTP,
//        name = "basicAuth",
//        scheme = "basic")
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


//        return new OpenAPI()
//                .info(new Info()
//                        .title("REST API")
//                        .description(description)
//                        .termsOfService("http://restapi.com")
//                        .version(version)
//
//                );


//           return new OpenAPI()
//                .info(new Info()
//                        .title("REST API")
//                        .description(description)
//                        .termsOfService("http://restapi.com")
//                        .version(version)
//
//                );
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("JavaInUse API")
//                .description("JavaInUse API reference for developers")
//                .termsOfServiceUrl("http://javainuse.com")
//                .contact("javainuse@gmail.com").license("JavaInUse License")
//                .licenseUrl("javainuse@gmail.com").version("1.0").build();
//    }

}