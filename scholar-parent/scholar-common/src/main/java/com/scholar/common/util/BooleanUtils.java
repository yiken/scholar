package com.scholar.common.util;

/**
 * 布尔值判断工具
 * @author yiealen
 *
 */
public class BooleanUtils {

    /**
     * 判断value是否为真
     * @param value
     * @return
     */
    public static boolean isTrue(boolean value){
        if(value == true){
            return true;
        }
        return false;
    }
    
    /**
     * 判断value是否为假
     * @param value
     * @return
     */
    public static boolean isFalse(boolean value){
        return !isTrue(value);
    }
}
