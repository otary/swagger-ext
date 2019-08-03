package cn.chenzw.swagger.ext.core.operation.provider;


import cn.chenzw.swagger.ext.core.constants.SwaggerConstants;
import cn.chenzw.swagger.ext.core.model.SwaggerModel;
import cn.chenzw.swagger.ext.core.model.SwaggerModelProperty;
import cn.chenzw.swagger.ext.core.util.SwaggerUtils;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Optional;
import io.swagger.annotations.ext.ApiMapParam;
import io.swagger.annotations.ext.ApiMapParams;
import io.swagger.annotations.ext.ApiMapResponse;
import io.swagger.annotations.ext.ApiMapResponses;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationModelsProviderPlugin;
import springfox.documentation.spi.service.contexts.RequestMappingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Map Model对象创建器
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SwaggerMapModelsProvider implements OperationModelsProviderPlugin {

    private TypeResolver typeResolver;

    private TypeNameExtractor nameExtractor;

    public SwaggerMapModelsProvider(TypeResolver typeResolver, TypeNameExtractor nameExtractor) {
        this.typeResolver = typeResolver;
        this.nameExtractor = nameExtractor;
    }


    @Override
    public void apply(RequestMappingContext context) {

        // 方法上的注解处理
        List<ApiMapParams> apiMapParamsList = context.findAnnotations(ApiMapParams.class);
        for (ApiMapParams apiMapParams : apiMapParamsList) {
            createParamterModel(context, apiMapParams);
        }

        // 参数上的注解处理
        List<ResolvedMethodParameter> parameters = context.getParameters();
        for (ResolvedMethodParameter parameter : parameters) {
            Optional<ApiMapParams> apiMapParamsMethodOptional = parameter.findAnnotation(ApiMapParams.class);
            if (apiMapParamsMethodOptional.isPresent()) {
                createParamterModel(context, apiMapParamsMethodOptional.get());
            }
        }

        // 返回对象处理
        Optional<ApiMapResponses> apiMapResponsesOptional = context.findAnnotation(ApiMapResponses.class);
        if (apiMapResponsesOptional.isPresent()) {
            createResponseModel(context, apiMapResponsesOptional.get());
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }


    /**
     * 创建参数对应的Model对象
     */
    private void createParamterModel(RequestMappingContext context, ApiMapParams apiMapParams) {

        Map<String, SwaggerModelProperty> properties = new HashMap<>();
        for (ApiMapParam apiMapParam : apiMapParams.value()) {
            properties.put(apiMapParam.key(), new SwaggerModelProperty(
                    apiMapParam.key(),
                    apiMapParam.example(),
                    apiMapParam.description(),
                    apiMapParam.defaultValue(),
                    apiMapParam.required(),
                    apiMapParam.allowableValues(),
                    apiMapParam.access(),
                    apiMapParam.allowMultiple(),
                    apiMapParam.dataTypeClass(),
                    apiMapParam.paramType(),
                    apiMapParam.format(),
                    apiMapParam.readOnly(),
                    apiMapParam.collectionFormat()
            ));
        }
        Class refModel = createRefModel(new SwaggerModel(apiMapParams.name(), SwaggerUtils.generateUniqueModelName(apiMapParams.name(), getRequestUri(context)), properties));

        if (refModel == null) {
            return;
        }
        context.getDocumentationContext().getAdditionalModels().add(typeResolver.resolve(refModel));
    }

    private void createResponseModel(RequestMappingContext context, ApiMapResponses apiMapResponses) {

        Map<String, SwaggerModelProperty> properties = new HashMap<>();
        ApiMapResponse[] apiMapResponseList = apiMapResponses.value();

        for (ApiMapResponse apiMapResponse : apiMapResponseList) {
            properties.put(apiMapResponse.key(), new SwaggerModelProperty(
                    apiMapResponse.key(),
                    apiMapResponse.example(),
                    apiMapResponse.description(),
                    apiMapResponse.defaultValue(),
                    apiMapResponse.required(),
                    apiMapResponse.allowableValues(),
                    apiMapResponse.access(),
                    apiMapResponse.allowMultiple(),
                    apiMapResponse.dataTypeClass(),
                    apiMapResponse.paramType(),
                    apiMapResponse.format(),
                    apiMapResponse.readOnly(),
                    apiMapResponse.collectionFormat()
            ));
        }
        Class refModel = createRefModel(new SwaggerModel(SwaggerConstants.RESPONSE_MODEL_NAME, SwaggerUtils.generateUniqueModelName(SwaggerConstants.RESPONSE_MODEL_NAME, getRequestUri(context)), properties));

        if (refModel == null) {
            return;
        }
        context.getDocumentationContext().getAdditionalModels().add(typeResolver.resolve(refModel));
    }

    /**
     * 动态生成参数Model对象
     *
     * @param swaggerModel
     * @return
     */
    private Class createRefModel(SwaggerModel swaggerModel) {
        ClassPool classPool = ClassPool.getDefault();

        CtClass ctClass = classPool.makeClass(SwaggerConstants.DEFAULT_MODEL_PACKAGE + swaggerModel.getTypeName());
        try {
            Map<String, SwaggerModelProperty> properties = swaggerModel.getProperties();
            for (Map.Entry<String, SwaggerModelProperty> modelPropertyEntry : properties.entrySet()) {
                ctClass.addField(createField(modelPropertyEntry.getValue(), ctClass));
            }
            return ctClass.toClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private CtField createField(SwaggerModelProperty modelProperty, CtClass ctClass) throws NotFoundException, CannotCompileException {
        CtField ctField = new CtField(ClassPool.getDefault().get(modelProperty.getDataTypeClass().getName()), modelProperty.getName(), ctClass);
        ctField.setModifiers(Modifier.PUBLIC);

        ConstPool constPool = ctClass.getClassFile().getConstPool();
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation("io.swagger.annotations.ApiModelProperty", constPool);
        annotation.addMemberValue("value", new StringMemberValue(modelProperty.getDescription(), constPool));
        annotation.addMemberValue("example", new StringMemberValue(modelProperty.getExample(), constPool));
        annotation.addMemberValue("defaultValue", new StringMemberValue(modelProperty.getDefaultValue(), constPool));
        annotation.addMemberValue("required", new BooleanMemberValue(modelProperty.isRequired(), constPool));
        annotation.addMemberValue("allowableValues", new StringMemberValue(modelProperty.getAllowableValues(), constPool));
        annotation.addMemberValue("allowMultiple", new BooleanMemberValue(modelProperty.isAllowMultiple(), constPool));
        annotation.addMemberValue("readOnly", new BooleanMemberValue(modelProperty.isReadOnly(), constPool));
        annotation.addMemberValue("format", new StringMemberValue(modelProperty.getFormat(), constPool));
        annotation.addMemberValue("collectionFormat", new StringMemberValue(modelProperty.getCollectionFormat(), constPool));
        annotation.addMemberValue("paramType", new StringMemberValue(modelProperty.getParamType(), constPool));
        annotation.addMemberValue("dataTypeClass", new ClassMemberValue(modelProperty.getDataTypeClass().getName(), constPool));
        attr.addAnnotation(annotation);
        ctField.getFieldInfo().addAttribute(attr);

        return ctField;
    }


    private String getRequestUri(RequestMappingContext context) {
        Set<String> patterns = context.getPatternsCondition().getPatterns();

        for (String pattern : patterns) {
            return pattern;
        }
        return "";
    }

}
