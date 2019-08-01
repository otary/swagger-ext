package cn.chenzw.swagger.ext.core.operation.handler;

import springfox.documentation.service.ResolvedMethodParameter;

public class ParameterMapHandler implements ParameterHandler {

    @Override
    public boolean isMatched(ResolvedMethodParameter methodParameter) {


        return true;
    }
}
