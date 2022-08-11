package sn.gainde2000.pi.doc;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Bean
    public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("sn.gainde2000.pi.opt.controller"))
        //.paths(PathSelectors.ant("/*"))
        .build()
        .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
        "Générateur code OTP REST API",
        "Description: Permettre la configuration et la verification des codes OTP",
        "1.0.0",
        "Terms of service",
        new Contact("Industrialisation",
        "aseye@gainde2000.sn",
        "Gainde2000"),
        "License of API", "API license URL Gainde 2000", Collections.emptyList());
    }
    
   

}
