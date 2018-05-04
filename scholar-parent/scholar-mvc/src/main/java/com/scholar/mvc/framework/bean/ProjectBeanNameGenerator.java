package com.scholar.mvc.framework.bean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import com.scholar.mvc.framework.annotation.ActionHandler;

/**
 * bean名称构建工具
 *
 * @author yilean
 * @date 2018年4月27日 下午2:06:34
 */
public class ProjectBeanNameGenerator extends AnnotationBeanNameGenerator {
    private final Logger logger = LoggerFactory.getLogger(ProjectBeanNameGenerator.class);

    /**
     * bean名称创建
     */
    @Override
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry definitionRegistry) {
        String beanName = super.generateBeanName(beanDefinition, definitionRegistry);
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> bean = Class.forName(beanClassName);
            ActionHandler actionHander = bean.getAnnotation(ActionHandler.class);
            if (null != actionHander && StringUtils.isNotBlank(actionHander.name())) {
                beanName = actionHander.name();
            }
//            System.out.println(new org.json.JSONObject(beanDefinition).toString(4));
        } catch (ClassNotFoundException e) {
            // darling, something has happened!
            logger.error(e.getMessage(), e);
        }
        return beanName;
    }

}
