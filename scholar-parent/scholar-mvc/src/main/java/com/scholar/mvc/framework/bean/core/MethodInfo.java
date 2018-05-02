/**   
 * @title MethodInfo.java 
 * @package com.scholar.mvc.framework.bean 
 */
package com.scholar.mvc.framework.bean.core;

import java.lang.reflect.Method;
import java.util.List;

/** 
 *
 * @author yilean
 * @date 2018年4月27日 上午11:47:16  
*/

public class MethodInfo {

    private String methodName;
    private int parameterCount;
    private Method method;
    private List<ParameterInfo> parameterInfos;
    
    
    /** @return the methodName */
    public String getMethodName() {
        return methodName;
    }
    /** @param methodName the methodName to set */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    /** @return the parameterCount */
    public int getParameterCount() {
        return parameterCount;
    }
    /** @param parameterCount the parameterCount to set */
    public void setParameterCount(int parameterCount) {
        this.parameterCount = parameterCount;
    }
    /** @return the method */
    public Method getMethod() {
        return method;
    }
    /** @param method the method to set */
    public void setMethod(Method method) {
        this.method = method;
    }
    /** @return the parameterInfos */
    public List<ParameterInfo> getParameterInfos() {
        return parameterInfos;
    }
    /** @param parameterInfos the parameterInfos to set */
    public void setParameterInfos(List<ParameterInfo> parameterInfos) {
        this.parameterInfos = parameterInfos;
    }
}
