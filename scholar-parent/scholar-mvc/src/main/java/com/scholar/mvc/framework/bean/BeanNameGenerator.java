package com.scholar.mvc.framework.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/** 
 * bean名称构建工具
 *
 * @author yilean
 * @date 2018年4月27日 下午2:06:34  
 */
public class BeanNameGenerator implements org.springframework.beans.factory.support.BeanNameGenerator {

    /* @see org.springframework.beans.factory.support.BeanNameGenerator#generateBeanName(org.springframework.beans.factory.config.BeanDefinition, org.springframework.beans.factory.support.BeanDefinitionRegistry) */
    @Override
    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry definitionRegistry) {
        String beanName = beanDefinition.getFactoryBeanName();
        String beanClassName = beanDefinition.getBeanClassName();
        
        return null;
    }

}
