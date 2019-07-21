package io.swagger.annotations.ext;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface ApiGroup {

    /**
     * 分组名
     *
     * @return
     */
    String name();

}
