package io.swagger.annotations.ext;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
@Documented
public @interface ApiMapResponse {

    String key();

    String example() default "";

    String description() default "";

    String defaultValue() default "";

    boolean required() default false;

    String allowableValues() default "";

    String access() default "";

    boolean allowMultiple() default false;

    Class<?> dataTypeClass() default String.class;

    String paramType() default "";

    String format() default "";

    boolean readOnly() default false;

    String collectionFormat() default "";


}
