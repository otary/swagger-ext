package cn.chenzw.swagger.ext.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author chenzw
 */
@Configuration
@PropertySource({SwaggerProperties.DEFAULT_SWAGGER_PROPERTY_FILE_LOCATION,
        SwaggerProperties.DEFAULT_PROPERTY_FILE_LOCATION})
public class SwaggerProperties {

    public static final String DEFAULT_SWAGGER_PROPERTY_FILE_LOCATION = "classpath:swagger.properties";
    public static final String DEFAULT_PROPERTY_FILE_LOCATION = "classpath:application.properties";
    public static final String PROP_PREFIX = "swagger.";

    @Value("${" + PROP_PREFIX + "scan-basepackage}")
    private String basePackage;

    @Value("${" + PROP_PREFIX + "title}")
    private String title;

    @Value("${" + PROP_PREFIX + "description}")
    private String description;

    @Value("${" + PROP_PREFIX + "term-of-service-url}")
    private String termsOfServiceUrl;

    @Value("${" + PROP_PREFIX + "version}")
    private String version;

    @Value("${" + PROP_PREFIX + "contact.name}")
    private String contactName;

    @Value("${" + PROP_PREFIX + "contact.url}")
    private String contactUrl;

    @Value("${" + PROP_PREFIX + "contact.email}")
    private String contactEmail;

    @Value("${" + PROP_PREFIX + "license}")
    private String license;

    @Value("${" + PROP_PREFIX + "license-url}")
    private String licenseUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    @Override
    public String toString() {
        return "SwaggerProperties{" + "basePackage='" + basePackage + '\'' + ", title='" + title + '\''
                + ", description='" + description + '\'' + ", termsOfServiceUrl='" + termsOfServiceUrl + '\''
                + ", version='" + version + '\'' + ", contactName='" + contactName + '\'' + ", contactUrl='"
                + contactUrl + '\'' + ", contactEmail='" + contactEmail + '\'' + ", license='" + license + '\''
                + ", licenseUrl='" + licenseUrl + '\'' + '}';
    }
}
