package com.scholar.mvc.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.scholar.common.util.BooleanUtils;

import net.sf.cglib.beans.BeanMap;

/**
 * json与java类型的转换工具
 * 
 * @author yiealen
 *
 */
@Component
public class JSONTransformer {

    private final Logger logger = LoggerFactory.getLogger(JSONTransformer.class);
    public static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";
    private String[] supportDateFormats = { "yyyy-MM-dd'T'HH:mm:ssZZ", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm",
            "yyyy-MM-dd" };
    private String jsonDateFormat = "yyyy-MM-dd'T'HH:mm:ssZZ";

    public String[] getSupportDateFormats() {
        return this.supportDateFormats;
    }

    public void setSupportDateFormats(String[] supportDateFormats) {
        this.supportDateFormats = supportDateFormats;
    }

    public String getJsonDateFormat() {
        return this.jsonDateFormat;
    }

    public void setJsonDateFormat(String jsonDateFormat) {
        this.jsonDateFormat = jsonDateFormat;
    }

    public Object transform(Object o) throws JSONException {
        if ((o instanceof JSONObject)) {
            return transformJso2Pojo(o, null);
        }
        if ((o instanceof JSONArray)) {
            return transformJso2Pojo(o, null);
        }
        return transformPojo2Jso(o);
    }

    public Object transformJso2Pojo(Object o) {
        return transformJso2Pojo(o, null);
    }

    /**
     * 将json数据转换为java对象
     * @param object JSONArray或JSONObject
     * @param collectionClass 集合类型 List Set Arrays
     * @param actualClass 需要转换的java对象的实际类型
     * @return     
     * @author yiealen
     * @date 2018年3月24日 下午4:17:50
     */
    public Object transformJson2Pojo(Object object, Class<?> collectionClass, Class<?> actualClass) {
        if ((object == null) || (object == JSONObject.NULL)) {
            return null;
        }
        if (BooleanUtils.isFalse(object.getClass() == JSONArray.class)) {
            return transformJso2Pojo(object, actualClass);
        }
        JSONArray array = (JSONArray)object;
        if (array.length() == 0) {
            return null;
        }
        Collection<Object> objs = null;
        if (collectionClass == Set.class) {
            objs = new HashSet<Object>();
        } else {
            objs = new ArrayList<Object>();
        }
        for (int index = 0,length = array.length(); index < length; index ++) {
            try {
                objs.add(transformJso2Pojo(array.get(index), actualClass));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (collectionClass == Arrays.class) {
            @SuppressWarnings("rawtypes")
            List list = (List)objs;
            return list.toArray();
        }
        return objs;
    }
    
    /**
     * 将object（JSONArray或JSONObject）转换为type类型
     * 
     * @param object
     * @param type
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object transformJso2Pojo(Object object, Class type) {
        try {
            if ((object == null) || (object == JSONObject.NULL)) {
                return null;
            }
            if ((object instanceof JSONArray)) {
                Collection<Object> objs = null;
                if (type == java.util.Set.class) {
                    objs = new HashSet<Object>();
                } else {
                    objs = new ArrayList<Object>();
                }
                JSONArray items = (JSONArray) object;
                int i = 0;
                for (int l = items.length(); i < l; i++) {
                    Object item = items.get(i);
                    objs.add(transformJso2Pojo(item, type));
                }
                return objs;
            }
            if ((object instanceof JSONObject)) {
                JSONObject json = (JSONObject) object;
                if ((type != null)/* && (!json.has(DomainInfoConstant.ENTITY_NAME_FIELD))
                        && (!json.has(DomainInfoConstant.ENTITY_TYPE_FIELD))*/) {
                    return setValue(type, json, type);
                }
//                if ((json.has(DomainInfoConstant.ENTITY_NAME_FIELD))
//                        || (json.has(DomainInfoConstant.ENTITY_TYPE_FIELD))) {
//                    Class<?> entityClass = null;
//                    if (json.has(DomainInfoConstant.ENTITY_TYPE_FIELD)) {
//                        entityClass = Class.forName(json.getString(DomainInfoConstant.ENTITY_TYPE_FIELD));
//                    } else if (json.has(DomainInfoConstant.ENTITY_NAME_FIELD)) {
//                        entityClass = getEntityType(json.getString(DomainInfoConstant.ENTITY_NAME_FIELD));
//                    } else {
//                        
//                    }
//                    return setValue(type, json, entityClass);
//                }
                return setMapValue(type, json);
            }
            if (type == null) {
                return object;
            }
            if ((type == Date.class) && (String.class.isInstance(object))) {
                return DateUtils.parseDate((String) object, this.supportDateFormats);
            }
            if ((type == Double.class) || (type == Double.TYPE)) {
                return Double.valueOf(Double.parseDouble(String.valueOf(object)));
            }
            if ((type == Integer.class) || (type == Integer.TYPE)) {
                return Integer.valueOf(Integer.parseInt(String.valueOf(object)));
            }
            if (type == BigDecimal.class) {
                return NumberUtils.createBigDecimal(String.valueOf(object));
            }
            if (type.isEnum()) {
                return Enum.valueOf(type, object.toString());
            }
            return BeanUtils.convertType(object, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 为map赋值
     * 
     * @param type
     * @param json
     * @return
     * @throws JSONException
     */
    private Map<String, Object> setMapValue(Class<?> type, JSONObject json) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<?> iter = json.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            Object oval = json.get(key);
            Object nval = transformJso2Pojo(oval, type);
            map.put(key, nval);
        }
        return map;
    }

    /**
     * 赋值方法
     * 
     * @param type
     *            若entityClass是map类型且type不是map类型 则type是最终返回的类型
     * @param json
     *            用于为最终返回类型赋值的数据来源
     * @param entityClass
     *            entityClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws JSONException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Object setValue(Class<?> type, JSONObject json, Class<?> entityClass)
            throws InstantiationException, IllegalAccessException, JSONException {
        Object bean;
        if (entityClass == Map.class) {
            if (json.has("map")) {
                json = json.getJSONObject("map");
            }
            if (type == Map.class) {
                bean = setMapValue(type, json);
                return bean;
            }
            bean = type.newInstance();
        } else {
            bean = entityClass.newInstance();
        }
        List<String> normProps = BeanUtils.getFullInstanceDeclaredEntityFieldNamesWithout(bean.getClass(), Enum.class);
        BeanMap beanMap = BeanMap.create(bean);
        for (String prop : normProps) {
            if (json.has(prop)) {
                Object oval = json.get(prop);
                Class<?> propertyType = BeanUtils.getPropertyType(entityClass, prop);
                if ((propertyType != null) && ((propertyType == List.class)
                       /* || (propertyType == org.hibernate.mapping.Set.class)*/ || (propertyType == Collection.class))
                        && ((oval instanceof JSONArray))) {
                    try {
                        Field field = BeanUtils.getClassField(bean.getClass(), prop);
                        ParameterizedType pType = (ParameterizedType) field.getGenericType();
                        Object actualTypeArg = pType.getActualTypeArguments()[0];
                        if ((actualTypeArg instanceof Class)) {
                            propertyType = (Class<?>) pType.getActualTypeArguments()[0];
                        } else {
                            ParameterizedType cType = (ParameterizedType) bean.getClass().getGenericSuperclass();
                            propertyType = (Class<?>) cType.getActualTypeArguments()[0];
                        }
                    } catch (Exception e) {
                        this.logger.error(
                                String.format("json[%s] prop[%s] value[%s] propertyType[%s]", new Object[] {
                                        json.toString(), prop, String.valueOf(oval), String.valueOf(propertyType) }),
                                e);
                    }
                }
                Object nval = transformJso2Pojo(oval, propertyType);
                try {
                    beanMap.put(prop, nval);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        List<Field> enumFields = BeanUtils.getFullInstanceDeclaredEntityFields(bean.getClass(), Enum.class);
        for (Field field : enumFields) {
            String prop = field.getName();
            if (json.has(prop)) {
                if (json.isNull(prop)) {
                    beanMap.put(prop, null);
                } else if ((json.get(prop) != null) && (StringUtils.isNotBlank(json.getString(prop)))) {
                    String oval = json.getString(prop);

                    Class<Enum> enumType = (Class<Enum>) field.getType();
                    try {
                        Enum<?> nval = Enum.valueOf(enumType, oval);
                        beanMap.put(prop, nval);
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return bean;
    }

    /**
     * 将object转换为JSONArray或JSONObject
     * 
     * @param object
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("rawtypes")
    public Object transformPojo2Jso(Object object) throws JSONException {
        if (object == null) {
            return null;
        }
        if ((object instanceof Date)) {
            return DateFormatUtils.format((Date) object, this.jsonDateFormat);
        }
        if (BeanUtils.isBasicInstance(object)) {
            return object;
        }
        if ((object instanceof Enum)) {
            return ((Enum) object).name();
        }
        if ((object instanceof Collection)) {
            JSONArray jsonlist = new JSONArray();
            for (Object item : (Collection) object) {
                jsonlist.put(transformPojo2Jso(item));
            }
            return jsonlist;
        }
        if (object.getClass().isArray()) {
            JSONArray jsonlist = new JSONArray();
            for (Object item : (Object[]) object) {
                jsonlist.put(transformPojo2Jso(item));
            }
            return jsonlist;
        }
        if ((object instanceof Map)) {
            Map<?, ?> map = (Map) object;
            JSONObject json = new JSONObject();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                if ((entry.getKey() instanceof String)) {
                    json.put((String) entry.getKey(), transformPojo2Jso(entry.getValue()));
                }
            }
            return json;
        }
        JSONObject json = new JSONObject();
//        if ((object instanceof BaseDomain)) {
//            json.put(DomainInfoConstant.ENTITY_NAME_FIELD, getEntityName(object));
//        } else {
//            json.put(DomainInfoConstant.ENTITY_TYPE_FIELD, BeanUtils.getClass(object).getName());
//        }
        List<String> props = BeanUtils.getFullInstanceDeclaredEntityFieldNames(object.getClass());
        BeanMap beanMap = BeanMap.create(object);
        for (String prop : props) {
            Object oval = beanMap.get(prop);
            //坑爹的hibernate JavassistLazyInitializer
            //延迟加载bean对象时hibernate赋予bean对象的一个属性
//            if(ObjectUtils.isNotEmpty(oval) && JavassistLazyInitializer.class.isAssignableFrom(oval.getClass())){
//                continue;
//            }
            Object nval = transformPojo2Jso(oval);
            json.put(prop, nval);
        }
        return json;
    }

    /**
     * 获取object的entityName
     * 
     * @param object
     * @return
     */
    public static String getEntityName(Object object) {
        return DomainObjectUtils.getEntityName(object.getClass());
    }

    /**
     * 获取entityName获取entity类型
     * 
     * @param entityName
     * @return
     */
    public static Class<?> getEntityType(String entityName) {
        Class<?> type = DomainObjectUtils.getEntityType(entityName);
        try {
            return type == null ? Class.forName(entityName) : type;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Object.class;
    }
}
