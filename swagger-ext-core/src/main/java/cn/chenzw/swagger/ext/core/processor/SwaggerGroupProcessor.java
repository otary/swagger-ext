package cn.chenzw.swagger.ext.core.processor;

import cn.chenzw.swagger.ext.core.bean.DocketFactoryBean;
import cn.chenzw.swagger.ext.core.config.SwaggerProperties;
import cn.chenzw.swagger.ext.core.util.ResourceScannerUtils;
import com.google.common.collect.Sets;
import io.swagger.annotations.ext.ApiGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chenzw
 */
public class SwaggerGroupProcessor implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerGroupProcessor.class);

    SwaggerProperties swaggerProperties;

    public SwaggerGroupProcessor(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        long t1 = System.currentTimeMillis();
        logger.debug("Swagger start group processor");
        logger.debug("SwaggerProperties: {}", swaggerProperties);

        Set<Class<?>> apiGroupClazzs = null;
        try {
            apiGroupClazzs = ResourceScannerUtils
                    .scanClassFromAnnotation(swaggerProperties.getBasePackage(), new Class[]{ApiGroup.class});
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Scan for @ApiGroup with error! ", e);
        }

        Map<String, Set<String>> apiGroupMap = new HashMap<>();
        for (Class<?> apiGroupClazz : apiGroupClazzs) {
            ApiGroup apiGroup = apiGroupClazz.getAnnotation(ApiGroup.class);
            if (apiGroupMap.containsKey(apiGroup.name())) {
                apiGroupMap.get(apiGroup.name()).add(apiGroupClazz.getName());
            } else {
                apiGroupMap.put(apiGroup.name(), Sets.newHashSet(apiGroupClazz.getName()));
            }
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ((ConfigurableApplicationContext) applicationContext)
                .getBeanFactory();
        for (Map.Entry<String, Set<String>> apiGroupEntry : apiGroupMap.entrySet()) {
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(DocketFactoryBean.class)
                    .addConstructorArgValue(apiGroupEntry.getKey()).addConstructorArgValue(apiGroupEntry.getValue())
                    .addConstructorArgValue(swaggerProperties).getRawBeanDefinition();
            registry.registerBeanDefinition(BeanDefinitionReaderUtils.generateBeanName(beanDefinition, registry, false),
                    beanDefinition);
        }

        Map<String, Docket> docketMaps = applicationContext.getBeansOfType(Docket.class);
        logger.debug("Swagger init dockets: {}", docketMaps);

        long t2 = System.currentTimeMillis();
        logger.debug("Swagger finish group processor, cost {} ms", t2 - t1);
    }


}
