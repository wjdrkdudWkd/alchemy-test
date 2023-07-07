package com.example.alchemytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Alchemy Test API")
                .description("alchemy API test")
                .version("0.0.1")
                .build();
    }

    @Bean
    public Docket commonApi() {

        return new Docket(DocumentationType.OAS_30)               // swagger 3.0 version
                .useDefaultResponseMessages(false)          // swagger 에서 제공되는 공통 default response message 사용여부
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.alchemytest.controller"))
//                .apis(RequestHandlerSelectors.any()) // 모든 패키지
//                .paths(PathSelectors.ant("/api/**")) // 등록할 api url 패턴 지정
                .paths(PathSelectors.any()) // 모든 api url 패턴 등록
                .build();
    }


    // 공통 response message 생성
    private List<Response> response(){
        List<Response> responses = new ArrayList<>();
        responses.add(new ResponseBuilder().code("500").description("header 500 massage - system error").build());
        responses.add(new ResponseBuilder().code("405").description("header 405 massage - method not allowed").build());
        responses.add(new ResponseBuilder().code("403").description("header 403 massage - token error").build());
        return responses;
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
