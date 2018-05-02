/**   
 * @title ParameterResolver.java 
 * @package com.scholar.mvc.framework.parameter 
 */
package com.scholar.mvc.framework.parameter.api;

import javax.servlet.http.HttpServletRequest;

import com.scholar.mvc.framework.bean.core.ParameterInfo;

/** 
 *
 * @author yilean
 * @date 2018年4月27日 上午11:41:06  
*/

public interface ParameterResolver {
    
    Object resolve(HttpServletRequest request, ParameterInfo parameterInfo);
    
    boolean support(ParameterInfo parameterInfo);
}
