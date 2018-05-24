package com.scholar.mvc.framework.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import com.scholar.common.util.ObjectUtils;
import com.scholar.common.util.StringUtils;
import com.scholar.mvc.framework.annotation.ActionHandler;
import com.scholar.mvc.framework.annotation.ModuleConfig;



/**
 * spring 实例化bean的命名策略
 * 
 * @author yiealen
 *
 */
public class ProjectBeanNameGenerator extends AnnotationBeanNameGenerator {
	
	/**
	 * 构造bean名称
	 */
	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanName = super.generateBeanName(definition, registry);
		beanName = StringUtils.unCaptureString(beanName);
		final String beanClassName = definition.getBeanClassName();
		String customBeanName = getCustomBeanName(beanClassName);
		if(StringUtils.isNotBlank(customBeanName)){
			return customBeanName;
		}
		
		String projName = getClassProjectName(beanClassName);
		if ((projName != null) && (!beanName.startsWith(projName + "."))) {
			return projName + "." + beanName;
		}
		return beanName;
	}

	/**
	 * 根据注解获取bean名称
	 * @param beanClassName
	 * @return
	 */
	private String getCustomBeanName(final String beanClassName) {
		if(StringUtils.isNotBlank(beanClassName)){
			try {
				Class<?> clazz = Class.forName(beanClassName);
				ActionHandler annotation = clazz.getAnnotation(ActionHandler.class);
				if(ObjectUtils.isNotEmpty(annotation)){
					String name = annotation.name();
					if(StringUtils.isBlank(name)){
						name = annotation.value();
					}
					if(name.startsWith("/")){
						name = name.substring(1);
					}
					return name;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Map<String, String> projectNameMap = new HashMap<String, String>();

	/**
	 * 获取bean全名与前缀并保存
	 * @param className
	 * @return
	 */
	public static String getClassProjectName(String className) {
		if ((className == null) || (!className.contains("."))) {
			return null;
		}
		String packageName = className.replaceAll("\\.[^\\.]+$", "");
		String configName = packageName + "." + "ProjectConfig";
		if (projectNameMap.containsKey(configName)) {
			return (String) projectNameMap.get(configName);
		}
		try {
			Class<?> configClz = Class.forName(configName);
			ModuleConfig mc = (ModuleConfig) configClz.getAnnotation(ModuleConfig.class);
			String projName = null;
			if (mc != null) {
				projName = mc.name();
			} else {
				projName = (String) configClz.getField("NAME").get(null);
			}
			projectNameMap.put(configName, projName);
			return projName;
		} catch (Exception e) {
		}
		return getClassProjectName(packageName);
	}
}