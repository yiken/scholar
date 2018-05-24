package com.scholar.common.util;

import com.scholar.common.comstant.NumberConstant;

/**
 * UUID生成器
 * 
 * @author QianX
 *
 */
public class UUID {

    private static char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z' };

    /**
     * 按照给定长度{@code length}获取UUID标识
     * 
     * @param length
     * @return
     */
    public static String generateGUID(int length) {
        int charsLength = chars.length;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars[NumberUtils.getRadomWithNumber(charsLength)]);
        }
        return sb.toString();
    }

    /**
     * 获取默认长度UUID标识
     * @return
     */
    public static String generateGUID() {
        return generateGUID(NumberConstant.DEFAULT_UUID_LENGTH);
    }

    /**
     * 按照给定分隔符度{@code separator}获取UUID标识
     * @param separator
     * @return
     */
    public static String generateUUID(char separator) {
        if (separator != '-') {
            return java.util.UUID.randomUUID().toString().replace('-', separator);
        }
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * 使用默认分隔符获取UUID标识
     * @return
     */
    public static String generateUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
