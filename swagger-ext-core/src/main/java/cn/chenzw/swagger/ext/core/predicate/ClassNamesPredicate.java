package cn.chenzw.swagger.ext.core.predicate;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import springfox.documentation.RequestHandler;

import java.util.Set;

/**
 * 根据class文件名称过滤
 *
 * @author chenzw
 */
public class ClassNamesPredicate implements Predicate<RequestHandler> {

    private String[] classNames;

    public ClassNamesPredicate(String classNames) {
        this.classNames = classNames.split(",|;");
    }

    public ClassNamesPredicate(Set<String> classNameSets) {
        this.classNames = classNameSets.toArray(new String[classNameSets.size()]);
    }

    @Override
    public boolean apply(RequestHandler input) {
        return (Boolean) declaringClass(input).transform(handlerClassName()).or(true);

    }

    private Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

    private Function<Class<?>, Boolean> handlerClassName() {
        return input -> ArrayUtils.contains(classNames, input.getCanonicalName());
    }
}
