package cn.chenzw.swagger.ext.core.util;

import cn.chenzw.swagger.ext.core.constants.SwaggerConstants;

/**
 * Swagger工具类
 *
 * @author chenzw
 */
public class SwaggerUtils {

    private SwaggerUtils() {
    }


    /**
     * 生成唯一的Model名称
     *
     * @param parameterName
     * @param marker
     * @return
     */
    public static String generateUniqueModelName(String parameterName, Object marker) {
        return parameterName + "$" + Integer.toHexString(System.identityHashCode(marker));
    }

    public static String generateModelResponseName(Object marker) {
        return generateUniqueModelName(SwaggerConstants.RESPONSE_MODEL_NAME, marker);
    }

}
