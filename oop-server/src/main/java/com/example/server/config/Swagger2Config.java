package com.example.server.config;


/**
 * swagger2配置类
 * @Author jiawei
 * @Date 2/09/2022 22:41
 * @Version 1.0
 */


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())//给swagger添加全局令牌
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Online office platform ")
                .description("Online office platform ")
                .version("1.0")
                .contact(new Contact("example", "http:localhost:8080/doc.html","xxxx@xxxx.com"))
                .build();



    }
    // 1. 解决访问接口登录问题
    private List<ApiKey> securitySchemes(){
        //设置请求头信息
        List<ApiKey> result= new ArrayList<>();
        // 参数：api key 名字 { 准备的 key 名字，value 请求头 }
        ApiKey apiKey=new ApiKey("Authorization","Authorization","Header");
        result.add(apiKey);
        return result;
    };

    // 2. 解决访问接口登录问题
    private List<springfox.documentation.spi.service.contexts.SecurityContext> securityContexts() {
        // 设置需要登录认证的路径
        List<springfox.documentation.spi.service.contexts.SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath());
        return result;
    }

    // 3. 解决访问接口登录问题
    private springfox.documentation.spi.service.contexts.SecurityContext getContextByPath() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/hello/.*"))
                .forPaths(PathSelectors.regex("/register/.*"))
                .build();
    }

    // 4. 设置默认授权 - 解决访问接口登录问题
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        result.add(new SecurityReference("Authorization", scopes));
        return result;
    }

}
