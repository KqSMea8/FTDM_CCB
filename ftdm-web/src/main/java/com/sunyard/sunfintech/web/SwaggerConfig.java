package com.sunyard.sunfintech.web;
import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 【功能描述】
 *
 * @author wyc  2018/1/22.
 */
//@Configuration
//@EnableSwagger2
//@EnableWebMvc
public class SwaggerConfig  {
   // @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sunyard.sunfintech.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("FTDM RESTful APIs")
                .description("杭州信雅达泛泰")
                .termsOfServiceUrl("http://localhost:8080/ftdm-web/doc.html")
                .contact("ftdm@mail.com")
                .version("1.0")
                .build();
    }
}

