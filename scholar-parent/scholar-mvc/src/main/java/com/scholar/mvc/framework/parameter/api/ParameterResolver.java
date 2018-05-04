package com.scholar.mvc.framework.parameter.api;

import javax.servlet.http.HttpServletRequest;

import com.scholar.mvc.framework.bean.core.ParameterInfo;

/**
 * 参数解析
 * @author yilean
 * @date 2018年4月27日 上午11:41:06
 */

public interface ParameterResolver extends Comparable<ParameterResolver> {
    Integer getOrder();    
    
    Object resolve(HttpServletRequest request, ParameterInfo parameterInfo) throws Exception;

    boolean support(ParameterInfo parameterInfo);
    
    @Override
    public default int compareTo(ParameterResolver parameterResolver) {
        return this.getOrder().compareTo(parameterResolver.getOrder());
    }
}
