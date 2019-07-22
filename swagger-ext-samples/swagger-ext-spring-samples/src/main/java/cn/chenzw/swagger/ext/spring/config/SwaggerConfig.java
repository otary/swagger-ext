package cn.chenzw.swagger.ext.spring.config;

import cn.chenzw.swagger.ext.core.annotation.EnableSwaggerGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@EnableSwaggerGroup
public class SwaggerConfig {

    /**
     * 默认API分组
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.chenzw.swagger.ext.spring.samples"))
                .paths(PathSelectors.any())
                .build();

    }


}
