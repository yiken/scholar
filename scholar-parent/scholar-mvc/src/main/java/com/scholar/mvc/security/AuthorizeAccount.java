package com.scholar.mvc.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.scholar.mvc.core.AdminUser;
import com.scholar.mvc.core.api.Account;
import com.scholar.mvc.framework.util.LoginHelper;

/** 
 * 用户认证与授权
 *
 * @author yilean
 * @date 2018年5月2日 下午2:13:40  
 */
public class AuthorizeAccount extends AuthorizingRealm {

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (null != LoginHelper.getLoginUser()) {
            
        }
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        String username = upToken.getUsername();
        Account account = AdminUser.admin;
        if (StringUtils.isNotBlank(username) && username.equals(account.getName())) {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account, account.getPassword(), this.getClass().getSimpleName());
            LoginHelper.setLoginUser(account);
            return authenticationInfo;
        }
        return null;
    }

}
