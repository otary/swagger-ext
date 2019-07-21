package cn.chenzw.swagger.ext.core.config;

import cn.chenzw.swagger.ext.core.processor.SwaggerGroupProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author chenzw
 */
@Configuration
@Import({SwaggerProperties.class})
public class SwaggerGroupConfiguration {

    @Bean
    SwaggerGroupProcessor swaggerGroupProcessor(SwaggerProperties swaggerProperties) {
        return new SwaggerGroupProcessor(swaggerProperties);
    }

}
