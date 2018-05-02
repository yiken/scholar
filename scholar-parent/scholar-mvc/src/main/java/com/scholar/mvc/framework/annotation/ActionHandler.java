package com.scholar.mvc.framework.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * @author Administrator
 *
 */
public @interface ActionHandler {

    @AliasFor(value = "value")
    String name() default "";

    @AliasFor(value = "name")
    String value() default "";
}
