package com.scholar.mvc.framework.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求对象工具
 * 
 * @author yilean
 * @date 2018年5月2日 下午4:54:27
 */
public class RequestUtils {

    /**
     * 获取登录上下文
     * 
     * @return
     */
    public static HttpSession getHttpSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
}
