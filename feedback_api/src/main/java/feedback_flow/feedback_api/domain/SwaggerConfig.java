package feedback_flow.feedback_api.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

// @Configuration
// @EnableOpenApi
// @EnableWebMvc
public class SwaggerConfig {

    // @Bean
    // public Docket api() {

    // return new Docket(DocumentationType.OAS_30)
    // .select()
    // .apis(RequestHandlerSelectors
    // .basePackage("feedback_flow.feedback_api.domain.controller"))
    // .build().apiInfo(apiInfo());
    // }

    // private ApiInfo apiInfo() {
    // return new ApiInfoBuilder().title("Customer Feedback API").description(
    // "Api for customers to send their feedback whether it is a criticism, a
    // compliment or an suggestion")
    // .version("1.0")
    // .build();
    // }
}
