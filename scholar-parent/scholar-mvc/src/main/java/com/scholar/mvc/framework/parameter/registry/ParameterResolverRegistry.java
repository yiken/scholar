/**   
 * @title ParameterResolverRegistry.java 
 * @package com.scholar.mvc.framework.parameter.registry 
 */
package com.scholar.mvc.framework.parameter.registry;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.scholar.mvc.framework.bean.core.ParameterInfo;
import com.scholar.mvc.framework.parameter.api.ParameterResolver;
import com.scholar.mvc.framework.util.ApplicationContextUtils;

/** 
 * 参数解析注册中心
 * 
 * @author yilean
 * @date 2018年4月27日 上午11:41:42  
 */
@Component
public class ParameterResolverRegistry {
    
    private List<ParameterResolver> parameterResolvers;
    
    @PostConstruct
    public void init() {
        Map<String, ParameterResolver> resolvers = ApplicationContextUtils.getApplicationContext().getBeansOfType(ParameterResolver.class);
        if (null == resolvers) {
            return;
        }
        Collection<ParameterResolver> values = resolvers.values();
        parameterResolvers = new LinkedList<>(values);
    }
    
    /**
     * 解析方法参数
     * @param request
     * @param parameterInfo
     * @return 
     * @author yilean
     * @date 2018年4月27日 下午12:28:37
     */
    public Object resolveParameter(HttpServletRequest request, ParameterInfo parameterInfo) {
        for (ParameterResolver parameterResolver : parameterResolvers) {
            if (parameterResolver.support(parameterInfo)) {
                return parameterResolver.resolve(request, parameterInfo);
            }
        }
        return null;
    }
}
