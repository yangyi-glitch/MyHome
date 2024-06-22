package com.springdata.springdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean(value = "dockerBean")
    public Docket dockerBean() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(new ApiInfoBuilder()
                .description("# Knife4j RESTful APIs")
                .termsOfServiceUrl("https://doc.xiaominfo.com/")
                .contact(new Contact("杨溢","www.yangyi.com","110@phone.com"))
                .version("1.0")
                .build())

        .groupName("用户服务")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.springdata.springdata.controller"))
        .paths(PathSelectors.any())
        .build();
        return docket;
    }
}
