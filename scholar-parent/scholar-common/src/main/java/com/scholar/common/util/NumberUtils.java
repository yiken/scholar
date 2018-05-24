package com.scholar.common.util;

import com.scholar.common.comstant.NumberConstant;

public class NumberUtils {

    /**
     * 获取number的位数
     * 
     * @param number
     *            大于1的整型数据
     * @return
     */
    private static int getNumberBit(int number) {
        int tempNum = number / NumberConstant.INTEGER_TEN;
        if (tempNum >= NumberConstant.INTEGER_ONE) {
            return NumberConstant.INTEGER_ONE + getNumberBit(tempNum);
        } else {
            return NumberConstant.INTEGER_ONE;
        }
    }

    /**
     * 比较两个数的大小 等于返回{@code true}
     * <p>
     * 大于返回{@code true}
     * </p>
     * <p>
     * 小于返回{@code false}
     * </p>
     * 
     * @param compareNumber 比较数
     * @param imcompareNumber 被比较数
     * @return
     */
    private static boolean compareNumber(int compareNumber, int imcompareNumber) {
        return compareNumber >= imcompareNumber;
    }

    /**
     * 与1比较 等于返回{@code true}
     * <p>
     * 大于返回{@code true}
     * </p>
     * <p>
     * 小于返回{@code false}
     * </p>
     * 
     * @param compareNumber
     *            比较数
     * @return
     */
    private static boolean compareNumberToOne(int compareNumber) {
        return compareNumber(compareNumber, NumberConstant.INTEGER_ONE);
    }

    /**
     * 返回小于{@code baseNumber}的数
     * 
     * @param baseNumber
     * @param bit
     *            {@code baseNumber}的位数
     * @return
     */
    public static int getRadomBaseNumber(int baseNumber) {
        if (compareNumberToOne(baseNumber)) {
            int bit = getNumberBit(baseNumber);
            return generateNumberBaseBit(bit) % baseNumber;
        }
        return NumberConstant.INTEGER_ZERO;
    }
    /**
     * 返回小于{@code baseNumber}的数
     * 
     * @param baseNumber
     * @param bit
     *            {@code baseNumber}的位数
     * @return
     */
    public static int getRadomWithNumber(int baseNumber) {
        if (compareNumberToOne(baseNumber)) {
            int bit = getNumberBit(baseNumber);
            return generateNumberWithBit(bit) % baseNumber;
        }
        return NumberConstant.INTEGER_ZERO;
    }

    /**
     * 返回基于所给两个数之间的随机数
     * <p>
     * 若两个数相等 直接返回他们的值
     * <p>
     * 两个数字不分先后 i.e.两个数不必按从小到大赋值 可任意
     * 
     * @param firstNumber
     * @param lastNumber
     * @return
     */
    public static int getRandomBetweenBaseNumber(int firstNumber, int lastNumber) {
        int tempNumber = lastNumber - firstNumber;
        if (tempNumber > NumberConstant.INTEGER_ZERO) {
            return getRadomBaseNumber(tempNumber) + firstNumber;
        }
        return getRadomBaseNumber(Math.abs(tempNumber)) + lastNumber;
    }

    /**
     * 根据{@code baseNumber}获取乘以10的bit幂次方数
     * 
     * @param baseNumber
     * @param bit
     *            次方数
     * @return
     */
    private static int getNumberWithBitNum(int baseNumber, int bit) {
        if (bit > NumberConstant.INTEGER_ONE) {
            return getNumberWithBitNum(baseNumber * NumberConstant.INTEGER_TEN, --bit);
        }
        return baseNumber;
    }

    /**
     * 构造一个{@code bit}位的随机数
     * <p>
     * 长度可能小于{{@code bit}}位
     * 
     * @param bit
     */
    private static int generateNumberBaseBit(int bit) {
        int floorVlaue = (int) Math.floor(Math.random() * NumberConstant.INTEGER_TEN);
        if (bit > NumberConstant.INTEGER_ONE) {
            return getNumberWithBitNum(floorVlaue, bit) + generateNumberBaseBit(--bit);
        } else {
            return floorVlaue;
        }
    }
    
    private static int generateNumberWithBit(int bit){
        double random=Math.random();
        return generateNumberBaseBitAndRandom(bit, random);
    }
    
    private static int generateNumberBaseBitAndRandom(int bit,double random){
        double randomNum=(random*NumberConstant.INTEGER_TEN)%NumberConstant.INTEGER_ONE;
        if(bit>NumberConstant.INTEGER_ONE){
            return getNumberWithBitNum((int)Math.floor(random*NumberConstant.INTEGER_TEN), bit)+generateNumberBaseBitAndRandom(--bit, randomNum);
        }
        return (int)Math.floor(random*NumberConstant.INTEGER_TEN);
    }
}
