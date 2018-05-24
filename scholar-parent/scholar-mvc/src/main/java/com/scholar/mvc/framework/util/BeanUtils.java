package com.scholar.mvc.framework.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.aop.framework.Advised;

import com.scholar.common.util.StringUtils;

import net.sf.cglib.proxy.Factory;

/**
 * 
 * bean工具
 * 
 * @author yiealen
 *
 */
public abstract class BeanUtils {
    
    /**
     * 获取type的所有属性名称字符串
     * （自省）
     * @param type
     * @return
     */
    public static final String[] getPropertyNames(Class<?> type) {
        try {
            BeanInfo info = Introspector.getBeanInfo(type);
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            List<String> propList = new ArrayList<String>();
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].getPropertyType() != Class.class) {
                    propList.add(properties[i].getName());
                }
            }
            return propList.toArray(new String[0]);
        } catch (IntrospectionException e) {
        }
        return null;
    }

    /**
     * 获取type的所有属性名称字符串
     * （反射）
     * @param type
     * @return
     */
    public static final String[] getDeclaredFieldNames(Class<?> type) {
        List<String> list = new ArrayList<String>();
        for (Field field : type.getDeclaredFields()) {
            list.add(field.getName());
        }
        return list.toArray(new String[0]);
    }

    /**
     * 获取type类型所有属性名称字符串 包括static属性
     * （反射）
     * @param type
     * @return
     */
    public static final String[] getFullDeclaredFieldNames(Class<?> type) {
        List<String> list = new ArrayList<String>();
        Class<?> superClass = type.getSuperclass();
        if (superClass != null) {
            for (String name : getFullDeclaredFieldNames(superClass)) {
                if (!list.contains(name)) {
                    list.add(name);
                }
            }
        }
        for (Field field : type.getDeclaredFields()) {
            list.add(field.getName());
        }
        return list.toArray(new String[0]);
    }

    /**
     * 获取type类型属性名称为fieldName的属性
     * （反射）
     * @param type
     * @param fieldName
     * @return Field
     */
    public static final Field getClassField(Class<?> type, String fieldName) {
        try {
            return type.getDeclaredField(fieldName);
        } catch (Exception e) {
            if ((type.getSuperclass() != null) && (type.getSuperclass() != Object.class)) {
                return getClassField(type.getSuperclass(), fieldName);
            }
        }
        return null;
    }

    /**
     * 获取type类型方法名为methodName无参方法
     * （反射）
     * @param type
     * @param methodName
     * @return
     */
    public static final Method getClassMethod(Class<?> type, String methodName) {
        try {
            return type.getDeclaredMethod(methodName, new Class[0]);
        } catch (Exception e) {
            if ((type.getSuperclass() != null) && (type.getSuperclass() != Object.class)) {
                return getClassMethod(type.getSuperclass(), methodName);
            }
        }
        return null;
    }

    /**
     * 运行obj方法名为methodName的无参方法并返回结果
     * （反射）
     * @param obj
     * @param methodName
     * @return
     */
    public static final Object invokeMethod(Object obj, String methodName) {
        Method method = getClassMethod(obj.getClass(), methodName);
        if (method != null) {
            try {
                return method.invoke(obj, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取type所有属性名称字符串（不包括父类）
     * （反射）
     * @param type
     * @return
     */
    public static final String[] getInstanceDeclaredFieldNames(Class<?> type) {
        List<String> list = new ArrayList<String>();
        for (Field field : type.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }

    /**
     * 获取type所有属性名称字符串（不包括static属性）
     * （反射）
     * @param type
     * @return
     */
    public static final String[] getFullInstanceDeclaredFieldNames(Class<?> type) {
        List<String> list = new ArrayList<String>();
        Class<?> superClass = type.getSuperclass();
        if (superClass != null) {
            for (String name : getFullInstanceDeclaredFieldNames(superClass)) {
                if (!list.contains(name)) {
                    list.add(name);
                }
            }
        }
        for (Field field : type.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                list.add(field.getName());
            }
        }
        return list.toArray(new String[0]);
    }

    /**
     * 获取type类型所有基本类型的属性名称字符串
     * （自省）
     * @param type
     * @return
     */
    public static final String[] getBasicTypePropertyNames(Class<?> type) {
        try {
            BeanInfo info = Introspector.getBeanInfo(type);
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            List<String> propList = new ArrayList<String>();
            for (int i = 0; i < properties.length; i++) {
                if (isBasicType(properties[i].getPropertyType())) {
                    propList.add(properties[i].getName());
                }
            }
            return propList.toArray(new String[0]);
        } catch (IntrospectionException e) {
        }
        return null;
    }

    /**
     * 获取type类型与pattern相匹配的所有属性字符串
     * （自省）
     * @param type
     * @param patterns
     * @return
     */
    public static final String[] getPropertyNames(Class<?> type, String pattern) {
        try {
            BeanInfo info = Introspector.getBeanInfo(type);
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            List<String> propList = new ArrayList<String>();
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].getPropertyType() != Class.class) {
                    String propName = properties[i].getName();
                    if (StringUtils.matches(pattern, propName)) {
                        propList.add(propName);
                    }
                }
            }
            return propList.toArray(new String[0]);
        } catch (IntrospectionException e) {
        }
        return null;
    }

    /**
     * 获取type类型与patterns的每一个相匹配的所有属性字符串
     * （自省）
     * @param type
     * @param patterns
     * @return
     */
    public static final String[] getPropertyNames(Class<?> type, String[] patterns) {
        try {
            BeanInfo info = Introspector.getBeanInfo(type);
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            List<String> propList = new ArrayList<String>();
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].getPropertyType() != Class.class) {
                    String propName = properties[i].getName();
                    for (int j = 0; j < patterns.length; j++) {
                        if (StringUtils.matches(patterns[j], propName)) {
                            propList.add(propName);
                        }
                    }
                }
            }
            return propList.toArray(new String[0]);
        } catch (Exception e0) {
            e0.printStackTrace();
        }
        return null;
    }

    /**
     * 获取type类型的propName属性的类型
     * （自省）
     * @param type
     * @param propName
     * @return
     */
    public static final Class<?> getPropertyType(Class<?> type, String propName) {
        try {
            BeanInfo info = Introspector.getBeanInfo(type);
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            for (int i = 0; i < properties.length; i++) {
                PropertyDescriptor property = properties[i];
                if (property.getName().equals(propName)) {
                    return property.getPropertyType();
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断type类型是否拥有propName属性
     * （自省）
     * @param type
     * @param propName
     * @return
     */
    public static final boolean hasProperty(Class<?> type, String propName) {
        try {
            BeanInfo info = Introspector.getBeanInfo(type);
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            for (int i = 0; i < properties.length; i++) {
                PropertyDescriptor property = properties[i];
                if (property.getName().equals(propName)) {
                    return true;
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断obj对象是否拥有propName属性
     * （自省）
     * @param obj
     * @param propName
     * @return
     */
    public static final boolean hasProperty(Object obj, String propName) {
        return hasProperty(obj.getClass(), propName);
    }

    /**
     * 将obj转换为type类型
     * （只包括基本类型和简单类型）
     * @param obj
     * @param type
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final Object convertType(Object obj, Class type) {
        if (type == null) {
            return obj;
        }
        String objStr = null;
        if (obj == null) {
            return null;
        }
        if ((obj.getClass() == type) || (type.isAssignableFrom(obj.getClass()))) {
            return obj;
        }
        if ((obj instanceof String)) {
            objStr = (String) obj;
        } else if ((obj instanceof java.util.Date)) {
            objStr = String.valueOf(((java.util.Date) obj).getTime());
        } else {
            objStr = obj.toString();
        }
        if (type == String.class) {
            return objStr;
        }
        if (type == Boolean.class) {
            return Boolean.valueOf(objStr);
        }
        if (type == Boolean.TYPE) {
            return Boolean.valueOf(Boolean.parseBoolean(objStr));
        }
        if (type == Integer.class) {
            return Integer.valueOf(truncateDecimalPart(objStr));
        }
        if (type == Integer.TYPE) {
            return Integer.valueOf(Integer.parseInt(truncateDecimalPart(objStr)));
        }
        if (type == Long.class) {
            return Long.valueOf(truncateDecimalPart(objStr));
        }
        if (type == Long.TYPE) {
            return Long.valueOf(Long.parseLong(truncateDecimalPart(objStr)));
        }
        if (type == Float.class) {
            return Float.valueOf(objStr);
        }
        if (type == Float.TYPE) {
            return Float.valueOf(Float.parseFloat(objStr));
        }
        if (type == Double.class) {
            return Double.valueOf(objStr);
        }
        if (type == Double.TYPE) {
            return Double.valueOf(Double.parseDouble(objStr));
        }
        if (type == BigDecimal.class) {
            return new BigDecimal(objStr);
        }
        if (type == java.util.Date.class) {
            try {
                return new java.util.Date(Long.parseLong(objStr));
            } catch (NumberFormatException e) {
                // return FormatUtils.smartParseDate(objStr);
            }
        }
        if (type.isEnum()) {
            return Enum.valueOf(type, objStr);
        }
        if(type == Byte.class){
            return Byte.valueOf(objStr);
        }
        if(type == Byte.TYPE){
            return Byte.valueOf(Byte.parseByte(objStr));
        }
        return obj;
    }

    /**
     * 判断clazz是否是简单类型
     * @param clazz
     * @return
     */
    public static boolean isBasicType(Class<?> clazz) {
        return (clazz == String.class) || (clazz == java.util.Date.class) || (clazz == java.sql.Date.class)
                || (clazz == Timestamp.class) || (clazz == Integer.class) || (clazz == Integer.TYPE)
                || (clazz == Long.class) || (clazz == Long.TYPE) || (clazz == Float.class) || (clazz == Float.TYPE)
                || (clazz == Double.class) || (clazz == Double.TYPE) || (clazz == Boolean.class)
                || (clazz == Boolean.TYPE) || (clazz == BigDecimal.class) || (clazz == Byte.class)
                || (clazz == Byte.TYPE || clazz.isEnum());
    }

    /**
     * 判断obj是否是基本java类型的实例
     * @param obj
     * @return
     */
    public static boolean isBasicInstance(Object obj) {
        if (obj == null) {
            return true;
        }
        return isBasicType(obj.getClass());
    }

    /**
     * 获取小数的整数部分
     * @param str
     * @return
     */
    private static String truncateDecimalPart(String str) {
        int pos = str.indexOf('.');
        if (pos != -1) {
            return str.substring(0, pos);
        }
        return str;
    }

    private static Map<Object, Class<?>> interfaceMap = new Hashtable<Object, Class<?>>();

    public static Class<?> getAssignableClass(Object object, Class<?> base) {
        if (interfaceMap.containsKey(object)) {
            return interfaceMap.get(object);
        }
        if ((object instanceof Advised)) {
            Advised advised = (Advised) object;
            try {
                Object target = advised.getTargetSource().getTarget();
                return getAssignableClass(target, base);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Class<?>[] interfaces = object.getClass().getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            Class<?> clz = interfaces[i];
            while (clz != null) {
                if (base.isAssignableFrom(clz)) {
                    Class<?> iClz = interfaces[i];
                    interfaceMap.put(object, iClz);
                    return iClz;
                }
                clz = clz.getSuperclass();
            }
        }
        return null;
    }

    /**
     * 返回object的类型  若object属于Factory类型则返回其父类
     * @param object
     * @return
     */
    public static Class<?> getClass(Object object) {
        if ((object instanceof Factory)) {
            return object.getClass().getSuperclass();
        }
        return object.getClass();
    }

    /**
     * 判断obj0与obj1是否相等
     * @param obj0
     * @param obj1
     * @return
     */
    public static boolean equalsIgnoreType(Object obj0, Object obj1) {
        if ((obj0 == null) && (obj1 == null)) {
            return true;
        }
        if (((obj0 == null) && (obj1 != null)) || ((obj0 != null) && (obj1 == null))) {
            return false;
        }
        if ((isBasicInstance(obj0)) && (isBasicInstance(obj1))) {
            return (obj0.hashCode() == obj1.hashCode()) || (String.valueOf(obj0).equals(String.valueOf(obj1)));
        }
        return obj0.equals(obj1);
    }

    static Map<String, List<String>> fullIDEntityFieldNamesMap = new Hashtable<String, List<String>>();

    /**
     * 获取entity所有的属性字符串，包括父类并保存（缓存）
     * （反射）
     * @param clz 要获取属性的entity
     * @return
     */
    public static final List<String> getFullInstanceDeclaredEntityFieldNames(Class<?> type) {
        String cacheKey = type.getName();
        List<String> names = fullIDEntityFieldNamesMap.get(cacheKey);
        if (names != null) {
            return new ArrayList<String>(names);
        }
        names = new ArrayList<String>();
        Class<?> superClass = type.getSuperclass();
        if ((superClass != null) && (superClass != Object.class)) {
            for (String name : getFullInstanceDeclaredEntityFieldNames(superClass)) {
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        for (Field field : type.getDeclaredFields()) {
            if ((!Modifier.isStatic(field.getModifiers()))) {
                String name = field.getName();
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        fullIDEntityFieldNamesMap.put(cacheKey, names);
        return names;
    }

    /**
     * 获取entity所有fieldType类型的属性字符串，包括父类并保存（缓存）
     * （反射）
     * @param clz 要获取属性的entity
     * @param fieldType 要获取的属性类型
     * @return
     */
    public static final List<String> getFullInstanceDeclaredEntityFieldNames(Class<?> classType, Class<?> fieldType) {
        String cacheKey = classType.getName() + "#" + fieldType.getName();
        List<String> names = fullIDEntityFieldNamesMap.get(cacheKey);
        if (names != null) {
            return new ArrayList<String>(names);
        }
        names = new ArrayList<String>();
        Class<?> superClass = classType.getSuperclass();
        if ((superClass != null) && (superClass != Object.class)) {
            for (String name : getFullInstanceDeclaredEntityFieldNames(superClass, fieldType)) {
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        for (Field field : classType.getDeclaredFields()) {
            if ((!Modifier.isStatic(field.getModifiers())) && (fieldType.isAssignableFrom(field.getType()))) {
                String name = field.getName();
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        fullIDEntityFieldNamesMap.put(cacheKey, names);
        return names;
    }

    /**
     * 获取entity所有给定fieldType类型之外的类型的属性字符串，包括父类并保存（缓存）
     * （反射）
     * @param clz 要获取属性的entity
     * @param fieldType 要获取的属性类型
     * @return
     */
    public static final List<String> getFullInstanceDeclaredEntityFieldNamesWithout(Class<?> classType,
            Class<?> fieldType) {
        String cacheKey = classType.getName() + "#!" + fieldType.getName();
        List<String> names = fullIDEntityFieldNamesMap.get(cacheKey);
        if (names != null) {
            return new ArrayList<String>(names);
        }
        names = new ArrayList<String>();
        Class<?> superClass = classType.getSuperclass();
        if ((superClass != null) && (superClass != Object.class)) {
            for (String name : getFullInstanceDeclaredEntityFieldNamesWithout(superClass, fieldType)) {
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        for (Field field : classType.getDeclaredFields()) {
            if ((!Modifier.isStatic(field.getModifiers())) && (!fieldType.isAssignableFrom(field.getType()))) {
                String name = field.getName();
                if (!names.contains(name)) {
                    names.add(name);
                }
            }
        }
        fullIDEntityFieldNamesMap.put(cacheKey, names);
        return names;
    }

    static Map<String, List<Field>> fullIDEntityFieldsMap = new Hashtable<String, List<Field>>();

    /**
     * 获取entity所有fieldType类型的属性，包括父类并保存（缓存）
     * （反射）
     * @param clz 要获取属性的entity
     * @param fieldType 要获取的属性类型
     * @return
     */
    public static final List<Field> getFullInstanceDeclaredEntityFields(Class<?> clz, Class<?> fieldType) {
        String cacheKey = clz.getName() + "#" + (fieldType == null ? null : fieldType.getName());
        List<Field> fields = fullIDEntityFieldsMap.get(cacheKey);
        if (fields != null) {
            return new ArrayList<Field>(fields);
        }
        fields = new ArrayList<Field>();
        Class<?> superClass = clz.getSuperclass();
        if ((superClass != null) && (superClass != Object.class)) {
            for (Field field : getFullInstanceDeclaredEntityFields(superClass, fieldType)) {
                if (!fields.contains(field)) {
                    fields.add(field);
                }
            }
        }
        for (Field field : clz.getDeclaredFields()) {
            if ((!Modifier.isStatic(field.getModifiers()))
                    && ((fieldType == null) || (fieldType.isAssignableFrom(field.getType())))
                    && (!fields.contains(field))) {
                fields.add(field);
            }
        }
        fullIDEntityFieldsMap.put(cacheKey, fields);
        return fields;
    }

    /**
     * 返回type类型的字符串名称
     * @param type
     * @return
     */
    private static String getTypeName(Class<?> type) {
        if (type == String.class) {
            return "string";
        }
        if ((type == Integer.class) || (type == Integer.TYPE)) {
            return "int";
        }
        if ((type == Long.class) || (type == Long.TYPE)) {
            return "long";
        }
        if ((type == Float.class) || (type == Float.TYPE)) {
            return "float";
        }
        if ((type == Double.class) || (type == Double.TYPE)) {
            return "double";
        }
        if (type.isAssignableFrom(Number.class)) {
            return "number";
        }
        if (type.isAssignableFrom(java.util.Date.class)) {
            return "date";
        }
        if ((type == Boolean.class) || (type == Boolean.TYPE)) {
            return "boolean";
        }
        if (type.isAssignableFrom(Enum.class)) {
            return "string";
        }
        return "object";
    }

    /**
     * 根据类型返回domain所有属性
     * （自省）
     * @param type
     * @return JSONObject
     * 格式：{fields:[name:'',type:'']}
     */
    public static JSONObject getMeta(Class<?> type) {
        JSONObject json = new JSONObject();
        try {
            BeanInfo info = Introspector.getBeanInfo(type);
            json.put("javaClass", type.getName());
            json.put("entityName", DomainObjectUtils.getEntityName(type));
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            List<String> props = Arrays.asList(getFullInstanceDeclaredFieldNames(type));
            JSONArray fields = new JSONArray();
            for (int i = 0; i < properties.length; i++) {
                try {
                    JSONObject field = new JSONObject();
                    String fieldName = properties[i].getName();
                    if (props.contains(fieldName)) {
                        String fieldType = getTypeName(properties[i].getPropertyType());
                        field.put("name", fieldName);
                        field.put("type", fieldType);

                        fields.put(field);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            json.put("fields", fields);
        } catch (Exception e) {
            return null;
        }
        return json;
    }
}
