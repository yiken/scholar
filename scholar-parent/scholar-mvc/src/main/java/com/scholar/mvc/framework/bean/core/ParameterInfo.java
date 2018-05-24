/**   
 * @title ParameterInfo.java 
 * @package com.scholar.mvc.framework.bean 
 */
package com.scholar.mvc.framework.bean.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.scholar.mvc.framework.bean.HttpRequestParameter;

/** 
 * 参数信息
 * @author yilean
 * @date 2018年4月27日 上午11:48:14  
 */
public class ParameterInfo {

    private String parameterName;
    private Class<?> type;
    private Annotation[] annotations;
    private Type[] genericTypes;
    
    private HttpRequestParameter httpRequestParameter;
    
    
    /** @return the parameterName */
    public String getParameterName() {
        return parameterName;
    }
    /** @param parameterName the parameterName to set */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    /** @return the type */
    public Class<?> getType() {
        return type;
    }
    /** @param type the type to set */
    public void setType(Class<?> type) {
        this.type = type;
    }
    /** @return the annotations */
    public Annotation[] getAnnotations() {
        return annotations;
    }
    /** @param annotations the annotations to set */
    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
    /** @return the genericTypes */
    public Type[] getGenericTypes() {
        return genericTypes;
    }
    /** @param genericTypes the genericTypes to set */
    public void setGenericTypes(Type[] genericTypes) {
        this.genericTypes = genericTypes;
    }
    /** @return the httpRequestParameter */
    public HttpRequestParameter getHttpRequestParameter() {
        return httpRequestParameter;
    }
    /** @param httpRequestParameter the httpRequestParameter to set */
    public void setHttpRequestParameter(HttpRequestParameter httpRequestParameter) {
        this.httpRequestParameter = httpRequestParameter;
    }
}
