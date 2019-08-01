package cn.chenzw.swagger.ext.core.operation.creator;

import cn.chenzw.swagger.ext.core.operation.handler.ParameterHandler;
import cn.chenzw.swagger.ext.core.operation.handler.ParameterMapHandler;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.swagger.annotations.ext.ApiMapParam;
import io.swagger.annotations.ext.ApiMapParams;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ParameterMapModelCreator implements OperationBuilderPlugin {

    /**
     * 动态生成的Class的包名
     */
    private static final String DEFAULT_MODEL_PACKAGE = "cn.chenzw.swagger.ext.model.";

    private ParameterHandler parameterHandler = new ParameterMapHandler();

    private TypeResolver typeResolver;

    public ParameterMapModelCreator(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Override
    public void apply(OperationContext context) {
        List<ResolvedMethodParameter> methodParameters = context.getParameters();
        for (ResolvedMethodParameter methodParameter : methodParameters) {
            if (parameterHandler.isMatched(methodParameter)) {
                ParameterContext parameterContext = new ParameterContext(methodParameter,
                        new ParameterBuilder(),
                        context.getDocumentationContext(),
                        context.getGenericsNamingStrategy(),
                        context);

                Optional<ApiMapParams> apiMapParamsOptional = methodParameter.findAnnotation(ApiMapParams.class);
                if (apiMapParamsOptional.isPresent()) {
                    ApiMapParams apiMapParams = apiMapParamsOptional.get();
                    parameterContext.getDocumentationContext().getAdditionalModels().add(typeResolver.resolve(createRefModel(apiMapParams.value(), apiMapParams.name())));
                }


            }
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }


   /* @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();

        // 参数类型为Map
        if (methodParameter.getParameterType().canCreateSubtype(Map.class)) {
            Optional<ApiMapParams> optional = methodParameter.findAnnotation(ApiMapParams.class);
            if (optional.isPresent()) {
                String name = optional.get().name();
                ApiMapParam[] properties = optional.get().value();

                ResolvedType resolve = typeResolver.resolve(createRefModel(properties, name));


                //向documentContext的Models中添加我们新生成的Class
                parameterContext.getDocumentationContext().getAdditionalModels().add(resolve);

                parameterContext.parameterBuilder()  //修改Map参数的ModelRef为我们动态生成的class
                        .parameterType("body")
                        .modelRef(new ModelRef("int"))
                        .name(name).description("hello");

               *//* parameterContext.parameterBuilder().name(name).description("sss")
                        .modelRef(new ModelRef("int")).order(-2147482648);*//*

            }
        }
    }*/


    private Class createRefModel(ApiMapParam[] propertys, String name) {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass(DEFAULT_MODEL_PACKAGE + name);

        try {
            for (ApiMapParam property : propertys) {
                ctClass.addField(createField(property, ctClass));
            }
            return ctClass.toClass();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private CtField createField(ApiMapParam property, CtClass ctClass) throws NotFoundException, CannotCompileException {
        CtField ctField = new CtField(ClassPool.getDefault().get(property.dataTypeClass().getName()), property.key(), ctClass);
        ctField.setModifiers(Modifier.PUBLIC);

        ConstPool constPool = ctClass.getClassFile().getConstPool();

        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation("io.swagger.annotations.ApiModelProperty", constPool);
        annotation.addMemberValue("value", new StringMemberValue(property.description(), constPool));
        if (ctField.getType().subclassOf(ClassPool.getDefault().get(String.class.getName()))) {
            annotation.addMemberValue("example", new StringMemberValue(property.example(), constPool));
        }
        if (ctField.getType().subclassOf(ClassPool.getDefault().get(Integer.class.getName()))) {
            annotation.addMemberValue("example", new IntegerMemberValue(Integer.parseInt(property.example()), constPool));
        }
        attr.addAnnotation(annotation);
        ctField.getFieldInfo().addAttribute(attr);

        return ctField;
    }


}
