package cn.chenzw.swagger.ext.core.operation.handler;

import springfox.documentation.service.ResolvedMethodParameter;

public interface ParameterHandler {

    boolean isMatched(ResolvedMethodParameter methodParameter);
}
