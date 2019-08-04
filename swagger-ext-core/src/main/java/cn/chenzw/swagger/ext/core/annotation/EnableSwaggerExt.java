package cn.chenzw.swagger.ext.core.annotation;

import cn.chenzw.swagger.ext.core.config.SwaggerExtConfiguration;
import cn.chenzw.swagger.ext.core.operation.processor.SwaggerMapProcessor;
import cn.chenzw.swagger.ext.core.operation.provider.SwaggerMapModelsProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
// @Import({SwaggerExtConfiguration.class})
@EnableSwaggerGroup
@ComponentScan(basePackageClasses = {SwaggerMapModelsProvider.class, SwaggerMapProcessor.class})
public @interface EnableSwaggerExt {
}
