package com.scholar.mvc.framework.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scholar.mvc.framework.util.BeanUtils;

/**
 * 封装请求参数
 * 
 * @author yilean
 * @date 2018年5月4日 下午4:41:25
 */
public class HttpRequestParameter {

    private Map<String, String[]> parameterMap;
    private HttpServletResponse   response;
    private HttpServletRequest    request;
    private String                serviceId;

    /**  */
    public HttpRequestParameter() {}

    /**  */
    public HttpRequestParameter(Map<String, String[]> parameterMap, HttpServletResponse response, HttpServletRequest request, String serviceId) {
        this.parameterMap = parameterMap;
        this.request = request;
        this.response = response;
        this.serviceId = serviceId;
    }

    public <T> T getParameter(String parameterName, Class<T> beanClass) {
        String[] parameter = this.parameterMap.get(parameterName);
        if (BeanUtils.isBasicType(beanClass)) {
            if (null != parameter && parameter.length > 0) {
                return beanClass.cast(BeanUtils.convertType(parameter[0], beanClass));
            }
        } else if (HttpServletResponse.class == beanClass) {
            return beanClass.cast(response);
        } else if (HttpServletRequest.class == beanClass) {
            return beanClass.cast(request);
        }
        return null;
    }

    public <T> Collection<T> getParameter(String parameterName, Class<T> beanClass, Class<?> collectionType) {
        String[] parameter = this.parameterMap.get(parameterName);
        if (BeanUtils.isBasicType(beanClass)) {
            if (List.class.isAssignableFrom(collectionType)) {
                List<T> list = new ArrayList<>();
                if (null != parameter && parameter.length > 0) {
                    for (String p : parameter) {
                        list.add(beanClass.cast(BeanUtils.convertType(p, beanClass)));
                    }
                    return list;
                }
            } else if (Set.class.isAssignableFrom(collectionType)) {
                Set<T> set = new HashSet<>();
                if (null != parameter && parameter.length > 0) {
                    for (String p : parameter) {
                        set.add(beanClass.cast(BeanUtils.convertType(p, beanClass)));
                    }
                    return set;
                }
            }
        }
        return null;
    }

    public Object[] getParameters(String parameterName, Class<?> beanClass) {
        String[] parameter = this.parameterMap.get(parameterName);
        if (BeanUtils.isBasicType(beanClass)) {
            List<Object> list = new ArrayList<>();
            if (null != parameter && parameter.length > 0) {
                for (String p : parameter) {
                    list.add(BeanUtils.convertType(p, beanClass));
                }
                return list.toArray();
            }
        }
        return null;
    }

    /** @return the serviceId */
    public String getServiceId() {
        return serviceId;
    }

    /** @return the parameterMap */
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    /** @return the response */
    public HttpServletResponse getResponse() {
        return response;
    }

    /** @return the request */
    public HttpServletRequest getRequest() {
        return request;
    }

    /** @param parameterMap the parameterMap to set */
    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    /** @param response the response to set */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /** @param request the request to set */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /** @param serviceId the serviceId to set */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
