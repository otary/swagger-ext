package cn.chenzw.swagger.ext.core.operation.processor;


import cn.chenzw.swagger.ext.core.util.SwaggerUtils;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import io.swagger.annotations.ext.ApiMapParams;
import io.swagger.annotations.ext.ApiMapResponses;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class SwaggerMapProcessor implements OperationBuilderPlugin {

    @Override
    public void apply(OperationContext context) {
        processParameter(context);
        processResponse(context);
    }


    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    /**
     * 处理参数对象
     *
     * @param context
     */
    private void processParameter(OperationContext context) {

        List<ApiMapParams> apiMapParamsList = context.findAllAnnotations(ApiMapParams.class);
        if (apiMapParamsList != null) {
            removeParameterBody(context, apiMapParamsList);
            addParamterBody(context, apiMapParamsList);
        }

        List<ResolvedMethodParameter> parameters = context.getParameters();
        for (ResolvedMethodParameter parameter : parameters) {
            Optional<ApiMapParams> apiMapMethodParamsOptional = parameter.findAnnotation(ApiMapParams.class);
            if (apiMapMethodParamsOptional.isPresent()) {
                List<ApiMapParams> apiMapParamsList2 = new ArrayList(Arrays.asList(apiMapMethodParamsOptional.get()));
                removeParameterBody(context, apiMapParamsList2);
                addParamterBody(context, apiMapParamsList2);
            }
        }
    }


    /**
     * 处理响应对象
     *
     * @param context
     */
    private void processResponse(OperationContext context) {
        Optional<ApiMapResponses> apiMapResponsesOptional = context.findAnnotation(ApiMapResponses.class);
        if (apiMapResponsesOptional.isPresent()) {
            context.operationBuilder().responseMessages(Sets.newHashSet(new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new ModelRef(SwaggerUtils.generateModelResponseName(context.requestMappingPattern())), null, null)));
        }
    }

    private boolean mapMatched(List<ApiMapParams> apiMapParamsList, String name) {
        for (ApiMapParams apiMapParams : apiMapParamsList) {
            if (apiMapParams.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void removeParameterBody(OperationContext context, List<ApiMapParams> apiMapParamsList) {
        OperationBuilder operationBuilder = context.operationBuilder();
        try {
            Field paramField = OperationBuilder.class.getDeclaredField("parameters");
            paramField.setAccessible(true);
            List<Parameter> parameters = (List<Parameter>) paramField.get(operationBuilder);

            parameters.removeIf(parameter -> {
                if ("body".equals(parameter.getParamType()) && mapMatched(apiMapParamsList, parameter.getName())) {
                    return true;
                }
                return false;
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Unable to modify parameter field!", e);
        }
    }

    private void addParamterBody(OperationContext context, List<ApiMapParams> apiMapParamsList) {
        List<Parameter> parameters = new ArrayList<>();
        for (ApiMapParams apiMapParams : apiMapParamsList) {
            ResolvedMethodParameter methodParameter = getMethodParameter(context.getParameters(), apiMapParams.name());

            ParameterContext parameterContext = new ParameterContext(methodParameter,
                    new ParameterBuilder(),
                    context.getDocumentationContext(),
                    context.getGenericsNamingStrategy(),
                    context);

            parameters.add(parameterContext.parameterBuilder()
                    .parameterType("body")
                    .modelRef(new ModelRef(SwaggerUtils.generateUniqueModelName(apiMapParams.name(), context.requestMappingPattern())))
                    .name(apiMapParams.name()).build());
        }
        context.operationBuilder().parameters(parameters);
    }

    /**
     * 获取对应name值的ResolvedMethodParameter对象
     *
     * @param methodParameters
     * @param name
     * @return
     */
    private ResolvedMethodParameter getMethodParameter(List<ResolvedMethodParameter> methodParameters, String name) {
        for (ResolvedMethodParameter methodParameter : methodParameters) {
            Optional<String> nameOptional = methodParameter.defaultName();
            if (nameOptional.isPresent() && nameOptional.get().equals(name)) {
                return methodParameter;
            }
        }
        return null;
    }
}
