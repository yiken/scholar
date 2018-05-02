package com.scholar.mvc.framework.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ TYPE })
/**
 * 模块配置
 *
 * @author yilean
 * @date 2018年4月27日 下午2:08:00
 */
public @interface ModuleConfig {
    String name() default "";
    String[] packages();
}
