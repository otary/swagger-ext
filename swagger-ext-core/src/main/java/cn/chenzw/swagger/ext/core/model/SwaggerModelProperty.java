package cn.chenzw.swagger.ext.core.model;

/**
 * @author chenzw
 */
public class SwaggerModelProperty {

    private String name;
    private String example;
    private String description;
    private String defaultValue;
    private boolean required;
    private String allowableValues;
    private String access;
    private boolean allowMultiple;
    private Class<?> dataTypeClass;
    private String paramType;
    private String format;
    private boolean readOnly;
    private String collectionFormat;


    public SwaggerModelProperty(String name, String example, String description, String defaultValue, boolean required, String allowableValues, String access, boolean allowMultiple, Class<?> dataTypeClass, String paramType, String format, boolean readOnly, String collectionFormat) {
        this.name = name;
        this.example = example;
        this.description = description;
        this.defaultValue = defaultValue;
        this.required = required;
        this.allowableValues = allowableValues;
        this.access = access;
        this.allowMultiple = allowMultiple;
        this.dataTypeClass = dataTypeClass;
        this.paramType = paramType;
        this.format = format;
        this.readOnly = readOnly;
        this.collectionFormat = collectionFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getAllowableValues() {
        return allowableValues;
    }

    public void setAllowableValues(String allowableValues) {
        this.allowableValues = allowableValues;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public boolean isAllowMultiple() {
        return allowMultiple;
    }

    public void setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public Class<?> getDataTypeClass() {
        return dataTypeClass;
    }

    public void setDataTypeClass(Class<?> dataTypeClass) {
        this.dataTypeClass = dataTypeClass;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getCollectionFormat() {
        return collectionFormat;
    }

    public void setCollectionFormat(String collectionFormat) {
        this.collectionFormat = collectionFormat;
    }

    @Override
    public String toString() {
        return "SwaggerModelProperty{" +
                "name='" + name + '\'' +
                ", example='" + example + '\'' +
                ", description='" + description + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", required=" + required +
                ", allowableValues='" + allowableValues + '\'' +
                ", access='" + access + '\'' +
                ", allowMultiple=" + allowMultiple +
                ", dataTypeClass=" + dataTypeClass +
                ", paramType='" + paramType + '\'' +
                ", format='" + format + '\'' +
                ", readOnly=" + readOnly +
                ", collectionFormat='" + collectionFormat + '\'' +
                '}';
    }
}
