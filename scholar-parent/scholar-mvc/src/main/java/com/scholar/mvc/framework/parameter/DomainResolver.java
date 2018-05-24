package com.scholar.mvc.framework.parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.scholar.mvc.framework.annotation.Pojo;
import com.scholar.mvc.framework.bean.core.ParameterInfo;
import com.scholar.mvc.framework.parameter.api.ParameterResolver;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author yilean
 * @date 2018年5月4日 下午5:24:00
 */
public class DomainResolver implements ParameterResolver {
    private Integer order = 100;

    @SuppressWarnings("unchecked")
    @Override
    public Object resolve(HttpServletRequest request, ParameterInfo parameterInfo) throws Exception {
        Type[] genericTypes = parameterInfo.getGenericTypes();
        Class<?> parameterType = parameterInfo.getType();
        String jsonStr = new String(IOUtils.toByteArray(request.getInputStream()));
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        
        if (null != genericTypes && genericTypes.length != 0) {
            Class<?> type = (Class<?>) genericTypes[0];
            JSONArray array = JSONArray.fromObject(jsonStr);
            if (List.class.isAssignableFrom(parameterType)) {
                return new ArrayList<Object>(JSONArray.toCollection(array, type));
            } else if (Set.class.isAssignableFrom(parameterType)) {
                return new HashSet<>(JSONArray.toCollection(array, type));
            } else if (parameterType.isArray()) {
                return JSONArray.toArray(array, type);
            }
        }
        return JSONObject.toBean(JSONObject.fromObject(jsonStr), parameterType);
    }

    @Override
    public boolean support(ParameterInfo parameterInfo) {
        Annotation[] annotations = parameterInfo.getAnnotations();
        if (null == annotations || annotations.length == 0) {
            return false;
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Pojo.class) {
                return true;
            }
        }
        return false;
    }

    /** @return the order */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

}
