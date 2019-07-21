package cn.chenzw.swagger.ext.core.bean;

import cn.chenzw.swagger.ext.core.config.SwaggerProperties;
import cn.chenzw.swagger.ext.core.predicate.ClassNamesPredicate;
import org.springframework.beans.factory.FactoryBean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Set;

/**
 * @author chenzw
 */
public class DocketFactoryBean implements FactoryBean<Docket> {

    private String groupName;
    private Set<String> clazzNames;
    private SwaggerProperties swaggerProperties;


    public DocketFactoryBean(String groupName, Set<String> clazzNames, SwaggerProperties swaggerProperties) {
        this.groupName = groupName;
        this.clazzNames = clazzNames;
        this.swaggerProperties = swaggerProperties;
    }

    @Override
    public Docket getObject() throws Exception {
        return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).apiInfo(apiInfo())
                .select()
                .apis(new ClassNamesPredicate(clazzNames))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                // 服务条款网址
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .version(swaggerProperties.getVersion())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .build();


    }

    @Override
    public Class<?> getObjectType() {
        return Docket.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
