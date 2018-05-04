package com.scholar.mvc.core;

import java.io.Serializable;

import com.scholar.mvc.core.api.Account;

/** 
 * 用户
 * 
 * @author yilean
 * @date 2018年5月2日 下午2:24:19  
 */
public class AdminUser implements Serializable, Account {

    /** long-AdminUser.java */
    private static final long serialVersionUID = -1130146989262563617L;
    
    public static AdminUser admin = new AdminUser("小白一号", "123456");

    private String id;
    private String name;
    private String password;
    
    
    /** 无参构造 */
    public AdminUser() {}
    
    /** 构造 */
    public AdminUser(String name,String password) {
        this.name = name;
        this.password = password;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /** @return the id */
    public String getId() {
        return id;
    }

    /** @param id the id to set */
    public void setId(String id) {
        this.id = id;
    }

}
