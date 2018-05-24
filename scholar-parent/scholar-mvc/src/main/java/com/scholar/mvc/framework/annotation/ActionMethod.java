package com.scholar.mvc.framework.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.scholar.mvc.framework.message.enumaration.MessageType;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ActionMethod {

    @AliasFor(value = "value")
    String name() default "";

    @AliasFor(value = "name")
    String value() default "";

    MessageType response();
}
