package com.scholar.mvc.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;

/** 
 * 登出过滤器
 * @author yilean
 * @date 2018年5月2日 下午2:50:59  
 */
public class AccountLogoutFilter extends LogoutFilter {
    
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        return super.preHandle(request, response);
    }
}
