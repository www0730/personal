package com.zzptc.twds.utils;

import java.security.MessageDigest;

import org.springframework.util.DigestUtils;


/**
 * 密码安全工具类
 */
public class MD5 {

	    //盐，用于混交md5
	    private static final String slat = "&%1A2Asc*&%$$#@";
	 
	 
	    /**
	     * spring框架里面的加密方法
	     *
	     * @param value 需要加密的字符串
	     * @return 加密后的字符串
	     */
	    public static String getMD5(String value) {
	        String base = value + "/s/" + slat;
	        return DigestUtils.md5DigestAsHex(base.getBytes());
	    }
	 
	    /**
	     * 原生的加密方法
	     *
	     * @param value 需要加密的字符串
	     * @return 加密后的字符串
	     */
	    public static String md5(String value) {
	        try {
	            value = value + slat;
	            MessageDigest messageDigest = MessageDigest.getInstance("MD5");//拿到一个MD5转换器
	            messageDigest.update(value.getBytes("UTF8"));//update传入的是字节类型，需要getbytes()方法生成字符串数组传入需要计算的字符串
	            byte message[] = messageDigest.digest();//通过digest()方法转换并返回结果，计算结果通过字节类型数组返回，包含16个元素
	            String result = "";//以下将计算的结果转换为字符串
	            for (int i = 0; i < message.length; i++) {
	                result += Integer.toHexString((0x000000FF & message[i]) | 0xFFFFFF00).substring(6);
	            }
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	

}
