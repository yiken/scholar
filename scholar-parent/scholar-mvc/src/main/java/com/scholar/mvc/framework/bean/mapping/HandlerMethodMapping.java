package com.scholar.mvc.framework.bean.mapping;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import com.scholar.mvc.framework.MvcConfig;
import com.scholar.mvc.framework.annotation.ActionHandler;
import com.scholar.mvc.framework.annotation.ActionMethod;
import com.scholar.mvc.framework.bean.core.HandlerInfo;
import com.scholar.mvc.framework.bean.core.MethodInfo;
import com.scholar.mvc.framework.bean.core.ParameterInfo;

/**
 * 请求处理映射
 * 
 * @author yilean
 * @date 2018年4月27日 下午2:39:08
 */
@Component(MvcConfig.PREFIX + "handlerMethodMapping")
public class HandlerMethodMapping {

    private Map<String, HandlerInfo> mapping;
    @Autowired
    private ApplicationContext       context;

    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @PostConstruct
    public void init() {
        if (null == mapping) {
            mapping = new LinkedHashMap<>();
        }
        initHandlerInfo();
    }

    private void initHandlerInfo() {
        Map<String, Object> handlerInfos = this.context.getBeansWithAnnotation(ActionHandler.class);
        if (null == handlerInfos || handlerInfos.isEmpty()) {
            return;
        }
        Set<Entry<String, Object>> entrySet = handlerInfos.entrySet();
        for (Entry<String, Object> entry : entrySet) {
            HandlerInfo handlerInfo = new HandlerInfo();
            handlerInfo.setHandler(entry.getValue());
            handlerInfo.setHandlerName(entry.getKey());
            handlerInfo.setMethodInfos(initMethodInfos(entry.getValue()));
            this.mapping.put(entry.getKey(), handlerInfo);
        }
    }

    /**
     * @param handler
     * @return
     * @author yilean
     * @date 2018年5月4日 上午9:25:04
     */
    private Map<String, MethodInfo> initMethodInfos(Object handler) {
        Class<? extends Object> handlerClass = handler.getClass();
        Method[] methods = handlerClass.getMethods();

        Map<String, MethodInfo> methodInfos = new LinkedHashMap<>();
        String methodName;
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                ActionMethod actionMethod = method.getAnnotation(ActionMethod.class);
                if (null == actionMethod) {
                    continue;
                }

                methodName = actionMethod.name();
                int parameterCount = method.getParameterCount();
                if (StringUtils.isBlank(methodName)) {
                    methodName = method.getName();
                }

                MethodInfo methodInfo = new MethodInfo();
                methodInfo.setMethod(method);
                methodInfo.setMethodName(methodName);
                methodInfo.setParameterCount(parameterCount);
                methodInfo.setMessageType(actionMethod.response());
                if (parameterCount > 0) {
                    methodInfo.setParameterInfos(initParameterInfos(method));
                }
                methodInfos.put(methodName, methodInfo);
            }
        }
        return methodInfos;
    }

    private List<ParameterInfo> initParameterInfos(Method method) {
        List<ParameterInfo> parameterInfos = new LinkedList<>();

        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = discoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();
        Type[] genericParameterTypes = method.getGenericParameterTypes();

        int index = 0;
        for (Class<?> parameterType : parameterTypes) {
            ParameterInfo parameterInfo = new ParameterInfo();
            parameterInfo.setType(parameterType);
            parameterInfo.setParameterName(parameterNames[index]);
            parameterInfo.setAnnotations(parameters[index].getAnnotations());

            // 查找真实类型
            Type type = genericParameterTypes[index];
            if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] genericTypes = parameterizedType.getActualTypeArguments();
                parameterInfo.setGenericTypes(genericTypes);
                continue;
            }
            if (GenericArrayType.class.isAssignableFrom(type.getClass())) {
                Type componentType = parameterType.getComponentType();
                Type[] genericTypes = new Type[] { componentType };
                parameterInfo.setGenericTypes(genericTypes);
            }

            parameterInfos.add(parameterInfo);
        }
        return parameterInfos;
    }
    
    public HandlerInfo getHandlerInfo(String handler) {
        return this.mapping.get(handler);
    }
    
}
