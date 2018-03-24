package com.zlxls.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * MD算法工具类
 * @ClassNmae：NewClass   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class MD5 {
    /**
     * @Description：全局数组
     */
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5","6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public MD5() {
    }
    /**
     * @Description：返回形式为数字跟字符串
     * @param bByte
     * @return 返回形式为数字跟字符串
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
    /**
     * @Description：返回形式只为数字
     * @param bByte
     * @return 返回形式只为数字
     */
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }
    /**
     * @Description：转换字节数组为16进制字串
     * @param bByte
     * @return 
     */
    private static String byteToString(byte[] bByte) {
        StringBuilder sBuffer = new StringBuilder();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    /**
     * @param strObj
     * @Description：获取MD5字符串
     * @return MD5字符串
     */
    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = strObj;
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
        }
        return resultString;
    }
}
