package com.soft.base.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AESUtil {

    @Value(value = "${aes.secret-factor}")
    private String secret;

    // AES 加密
    public String encrypt(String data) throws Exception {
        // 创建 AES 密钥
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "AES");

        // 创建 AES 加密器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16])); // IV 为全零

        // 执行加密
        byte[] encrypted = cipher.doFinal(data.getBytes());

        // 返回 Base64 编码的字符串
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // AES 解密
    public String decrypt(String encryptedData) throws Exception {
        // 创建 AES 密钥
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "AES");

        // 创建 AES 解密器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16])); // IV 为全零

        // 执行解密
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        // 返回解密后的字符串
        return new String(decrypted);
    }
}
