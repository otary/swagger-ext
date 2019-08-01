package io.swagger.annotations.ext;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
@Documented
public @interface ApiMapParam {

    /**
     * key值
     *
     * @return
     */
    String key();

    String example() default "";

    /**
     * 值类型
     *
     * @return
     */
    Class dataTypeClass() default String.class;

    String description() default "";
}
