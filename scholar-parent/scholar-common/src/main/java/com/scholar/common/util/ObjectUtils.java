package com.scholar.common.util;

/**
 * 对象判断工具
 * @author yiealen
 *
 */
public class ObjectUtils {
    
    /**
     * 判断是否为空
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object){
        if(null == object){
            return true;
        }
        return false;
    }
    
    /**
     * 判断是否不为空
     * @param object
     * @return
     */
    public static boolean isNotEmpty(Object object){
        return !isEmpty(object);
    }
}
