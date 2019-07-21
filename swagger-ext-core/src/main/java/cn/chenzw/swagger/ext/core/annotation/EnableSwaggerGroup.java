package cn.chenzw.swagger.ext.core.annotation;

import org.springframework.cache.annotation.CachingConfigurationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableSwaggerGroup {
}
