package com.scholar.common.util;

import java.util.regex.Pattern;



/**
 * 字符串工具
 * 
 * @author yiealen
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 使首字母大写
     * 
     * @param string
     * @return
     */
    public static String captureString(String string) {
        char[] chars = string.toCharArray();
        if(Character.isLowerCase(chars[0])){
            chars[0] -= 32;
            return String.valueOf(chars);
        }
        return string;
    }
    
    /**
     * 使首字母小写
     * @param string
     * @return
     */
    public static String unCaptureString(String string){
        char[] chars = string.toCharArray();
        if(Character.isUpperCase(chars[0])){
            chars[0] += 32;
            return String.valueOf(chars);
        }
        return string;
    }
    
    /**
     * 判断字符串不同
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean unEquals(CharSequence cs1, CharSequence cs2){
        return !equals(cs1, cs2);
    }
    
    /**
     * 判断字符串不同
     * 忽略大小写
     * @param cs1
     * @param cs2
     * @return
     */
    public static boolean unEqualsIgnoreCase(CharSequence cs1, CharSequence cs2){
        return !equalsIgnoreCase(cs1, cs2);
    }
    
    /**
     * 判断是否字符串与正则匹配
     * @param pattern
     * @param source
     * @return
     */
    public static final boolean matches(String pattern, String source)
      {
        if ((pattern.indexOf('*') != -1) || (pattern.indexOf('?') != -1)) {
          return Pattern.matches(pattern.replace(".", "\\.").replace("*", ".*") + "$", source);
        }
        return source.equals(pattern);
      }
    
    public static void main(String[] args) {
        String str = "ABCdd";
        String str3 = "ZABCdd";
        String str1 = "zABCdd";
        String str2 = "0aABCdd";
        
        System.out.println("str:" + captureString(str));
        System.out.println("str:" + unCaptureString(str));
        
        System.out.println("str1:" + captureString(str1));
        System.out.println("str1:" + unCaptureString(str1));
        
        System.out.println("str2:" + captureString(str2));
        System.out.println("str2:" + unCaptureString(str2));
        
        System.out.println("str3:" + captureString(str3));
        System.out.println("str3:" + unCaptureString(str3));
        
    }
}