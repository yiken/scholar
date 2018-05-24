package com.scholar.mvc.framework;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scholar.mvc.framework.bean.HttpRequestParameter;
import com.scholar.mvc.framework.bean.core.HandlerInfo;
import com.scholar.mvc.framework.bean.core.MethodInfo;
import com.scholar.mvc.framework.bean.core.ParameterInfo;
import com.scholar.mvc.framework.bean.mapping.HandlerMethodMapping;
import com.scholar.mvc.framework.message.enumaration.MessageType;
import com.scholar.mvc.framework.message.registry.MessageProcesserRegistry;
import com.scholar.mvc.framework.parameter.registry.ParameterResolverRegistry;

/**
 * 
 * @author yilen
 * @date 2018年5月24日 下午4:15:37
 */
@Controller
@RequestMapping("/scholar")
public class BaseSupportController {
    @Autowired
    private MessageProcesserRegistry  processerRegistry;
    @Autowired
    private ParameterResolverRegistry resolverRegistry;
    @Autowired
    private HandlerMethodMapping      mapping;

    @RequestMapping("/{handler}/{method}")
    public Object resolveRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String handler, @PathVariable String method) {
        return resolveRequest(request, response, handler, method, null);
    }

    @RequestMapping("/{handler}/{method}/{keyValue}")
    public Object resolveRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String handler, @PathVariable String method, String keyValue) {
        // 返回結果
        Object returnResult = null;
        try {
            HandlerInfo handlerInfo = this.mapping.getHandlerInfo(handler);
            MethodInfo methodInfo = handlerInfo.getMethodInfos().get(method);
            
            HttpRequestParameter httpRequestParameter = new HttpRequestParameter();
            Map<String, String[]> parameterMap = request.getParameterMap();

            Object handlerBean = handlerInfo.getHandler();
            Method handlerMethod = methodInfo.getMethod();
            // 方法参数列表
            Object[] parameters = null;
            // 响应类型
            MessageType messageType = methodInfo.getMessageType();

            //参数个数
            int parameterCount = methodInfo.getParameterCount();
            if (parameterCount > 0) {
                parameters = new Object[parameterCount];
                List<ParameterInfo> parameterInfos = methodInfo.getParameterInfos();
                int index = 0;
                for (ParameterInfo parameterInfo : parameterInfos) {
                    
                    Object parameter = this.resolverRegistry.resolveParameter(request, parameterInfo);
                    parameters[index] = parameter;
                    index ++;
                }
            }
            
            
            //执行handler方法
            returnResult = handlerMethod.invoke(handlerBean, parameters);
            //处理返回结果
            returnResult = this.processerRegistry.processMessage(returnResult, messageType);
        } catch (Exception e) {
            // darling, something has happened!
            e.printStackTrace();
        }

        return returnResult;
    }
}
