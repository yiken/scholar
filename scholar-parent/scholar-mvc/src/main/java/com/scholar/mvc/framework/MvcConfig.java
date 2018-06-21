package com.scholar.mvc.framework;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.scholar.mvc.framework.bean.ProjectBeanNameGenerator;

@Configuration
@ComponentScan(basePackages = {"com.scholar.mvc.framework.util", "com.scholar.mvc.framework" },
excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                  @ComponentScan.Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class) }, nameGenerator = ProjectBeanNameGenerator.class)
public class MvcConfig {
    public final static String NAME = "mvc";
    public final static String PREFIX = NAME + ".";
    
    @Autowired
    private ApplicationContext context;
    
    @PostConstruct
    public void init() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("what happened?");
        System.out.println(context.getApplicationName());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
