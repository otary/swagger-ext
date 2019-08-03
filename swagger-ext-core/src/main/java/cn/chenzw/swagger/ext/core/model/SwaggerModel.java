package cn.chenzw.swagger.ext.core.model;

import java.util.Map;

public class SwaggerModel {

    private String name;

    private String typeName;

    private Map<String, SwaggerModelProperty> properties;


    public SwaggerModel(String name, String typeName, Map<String, SwaggerModelProperty> properties) {
        this.name = name;
        this.typeName = typeName;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, SwaggerModelProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, SwaggerModelProperty> properties) {
        this.properties = properties;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "SwaggerModel{" +
                "name='" + name + '\'' +
                ", typeName='" + typeName + '\'' +
                ", properties=" + properties +
                '}';
    }
}
