package com.scholar.mvc.framework.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
/**
 * 标记简单java bean
 * @author yilean
 * @date 2018年5月4日 下午5:22:49
 */
public @interface Pojo {

}
