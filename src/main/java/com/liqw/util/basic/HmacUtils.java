package com.liqw.util.basic;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.liqw.util.basic.Base64;


/**
 * Created by liqw on 2017/11/23.
 *
 * 对数据生成安全码，用于校验数据的完整性
 */
public class HmacUtils {

    private static final String HMAC_ALGORITHM = "HmacMD5";

    public static final Charset ENCODING = Charset.forName("UTF-8");

    public static String createMacKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(HMAC_ALGORITHM);
            SecretKey generateKey = keyGenerator.generateKey();
            return Base64.encodeBytes(generateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static byte[] hmac(String hmacKey, String data) {
        Mac mac;
        try {
            SecretKey key = new SecretKeySpec(Base64.decode(hmacKey), HMAC_ALGORITHM);
            mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(key);
            byte[] doFinal = mac.doFinal(data.getBytes(ENCODING));
            return doFinal;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static byte[] md5(String plaintext) {
        assert plaintext != null : "plaintext may arg not be null";
        byte[] bytes = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(plaintext.getBytes(ENCODING));
            bytes = digest.digest();
            return bytes;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
