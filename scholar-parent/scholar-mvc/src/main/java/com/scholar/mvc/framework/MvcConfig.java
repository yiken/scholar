package com.scholar.mvc.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@ComponentScan(basePackages = { "com.scholar.mvc.framework" }, useDefaultFilters = false, 
excludeFilters = {@ComponentScan.Filter(
        type = FilterType.ANNOTATION, 
        classes = { Controller.class, ControllerAdvice.class }) })
public class MvcConfig {

}
