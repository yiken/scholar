package com.scholar.mvc.framework.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import com.scholar.common.util.StringUtils;
import com.scholar.mvc.framework.bean.ProjectBeanNameGenerator;


/**
 * domain对象工具
 * 
 * @author yiealen
 *
 */
public abstract class DomainObjectUtils {

    /**
     * 获取domain get方法
     * @param type
     * @param propName
     * @return
     */
    public static Method getGetMethod(Class<?> type, String propName) {
        String methodName = "get" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
        try {
            return type.getMethod(methodName, new Class[0]);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取domain set方法
     * @param type
     * @param propName
     * @return
     */
    public static Method getSetMethod(Class<?> type, String propName) {
        String methodName = "set" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
        try {
            return type.getMethod(methodName, new Class[0]);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    static Map<Class<?>, String> entityNames = new HashMap<Class<?>, String>();
    static Map<String, Class<?>> entityTypes = new HashMap<String, Class<?>>();

    /**
     * 获取entity名称
     * @param type
     * @return
     */
    public static String getEntityName(Class<?> type) {
        String name = (String) entityNames.get(type);
        if (name != null) {
            return name;
        }
        Entity de = (Entity) type.getAnnotation(Entity.class);
        if (de != null) {
            name = de.name();
        } else {
            name = ProjectBeanNameGenerator.getClassProjectName(type.getName());
            if (name == null) {
                name = type.getName();
            } else {
                name = name + "." + StringUtils.unCaptureString(type.getSimpleName());
            }
        }
        entityNames.put(type, name);
        entityTypes.put(name, type);
        return name;
    }

    /**
     * 获取entity类型
     * @param entityName
     * @return
     */
    public static Class<?> getEntityType(String entityName) {
        return (Class<?>) entityTypes.get(entityName);
    }
}
