package com.scholar.mvc.core.api;

/**
 * 用户类付需要实现的接口
 *
 * @author yilean
 * @date 2018年5月2日 下午2:14:57
 */
public interface Account {
    
    String getName();

    String getPassword();

    /** 
     * @return     
     * @author yilean
     * @date 2018年5月2日 下午5:00:09
     */
    String getId();
}
