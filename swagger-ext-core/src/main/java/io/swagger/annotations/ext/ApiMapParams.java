package io.swagger.annotations.ext;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Inherited
@Documented
public @interface ApiMapParams {

    ApiMapParam[] value();

    String name();
}
