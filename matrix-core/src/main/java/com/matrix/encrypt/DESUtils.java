package com.matrix.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 *@description:DES加解密工具类
 *
 *@param
 *@author liwt
 *@date 2019/8/12 20:45
 *@return
 *@version 1.0.1
*/
public class DESUtils {
	/** 默认key */
	public final static String KEY = "SLcWTTKC0XhadTHT3Al0QIDAQAB";
	
	/**
	 *@description:DES加密
	 *
	 *@param
	 *@author liwt
	 *@date 2019/8/12 20:46
	 *@return
	 *@version 1.0.1
	*/
    @SuppressWarnings("restriction")
	public static String encrypt(String data,String key) {  
        String encryptedData = null;  
        try {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(key.getBytes("utf-8"));  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(deskey);  
            // 加密对象  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);  
            // 加密，并把字节数组编码成字符串  
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes("utf-8")));  
        } catch (Exception e) {  
            throw new RuntimeException("加密错误，错误信息：", e);  
        }  
        return encryptedData;  
    }  
	
    /**
     *@description:DES解密
     *
     *@param
     *@author liwt
     *@date 2019/8/12 20:46
     *@return
     *@version 1.0.1
    */
    @SuppressWarnings("restriction")
	public static String decrypt(String cryptData,String key) {  
        String decryptedData = null;  
        try {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(key.getBytes("utf-8"));  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(deskey);  
            // 解密对象  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);  
            // 把字符串解码为字节数组，并解密  
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)),"utf-8");  
        } catch (Exception e) {  
            throw new RuntimeException("解密错误，错误信息：", e);  
        }  
        return decryptedData;  
    }

}
