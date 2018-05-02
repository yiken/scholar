/**   
 * @title HandlerInfo.java 
 * @package com.scholar.mvc.framework.bean 
 */
package com.scholar.mvc.framework.bean.core;

import java.util.Map;

/** 
 *
 * @author yilean
 * @date 2018年4月27日 上午11:46:24  
*/
public class HandlerInfo {

    private String handlerName;
    private Object handler;
    private Map<String, MethodInfo> methodInfos;
    
    
    /** @return the handlerName */
    public String getHandlerName() {
        return handlerName;
    }
    /** @param handlerName the handlerName to set */
    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }
    /** @return the handler */
    public Object getHandler() {
        return handler;
    }
    /** @param handler the handler to set */
    public void setHandler(Object handler) {
        this.handler = handler;
    }
    /** @return the methodInfos */
    public Map<String, MethodInfo> getMethodInfos() {
        return methodInfos;
    }
    /** @param methodInfos the methodInfos to set */
    public void setMethodInfos(Map<String, MethodInfo> methodInfos) {
        this.methodInfos = methodInfos;
    }
}
