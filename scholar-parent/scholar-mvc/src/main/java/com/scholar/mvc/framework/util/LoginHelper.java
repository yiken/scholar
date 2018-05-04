package com.scholar.mvc.framework.util;

import javax.servlet.http.HttpSession;

import com.scholar.mvc.core.api.Account;

/**
 * 登陆用户管理
 * @author yilean
 * @date 2018年5月2日 下午4:55:38
 */
public class LoginHelper {
    
    private final static String LOGIN_USER    = "LOGIN_USER";
    private final static String LOGIN_USER_ID = "LOGIN_USER_ID";

    
    public static Account getLoginUser() {
        return (Account) RequestUtils.getHttpSession().getAttribute(LOGIN_USER);
    }

    public static String getLoginUserId() {
        return (String) RequestUtils.getHttpSession().getAttribute(LOGIN_USER_ID);
    }

    public static void setLoginUser(Account account) {
        HttpSession httpSession = RequestUtils.getHttpSession();
        httpSession.setAttribute(LOGIN_USER, account);
        httpSession.setAttribute(LOGIN_USER_ID, account.getId());
    }

    public static void removeLoginUser() {
        RequestUtils.getHttpSession().removeAttribute(LOGIN_USER);
        RequestUtils.getHttpSession().removeAttribute(LOGIN_USER_ID);
    }
}
