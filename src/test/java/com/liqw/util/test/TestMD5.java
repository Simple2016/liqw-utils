package com.liqw.util.test;

import com.liqw.util.basic.Base64;
import com.liqw.util.basic.HmacUtils;
import org.junit.Test;

/**
 * Created by liqw on 2017/11/23.
 */
public class TestMD5 {
    @Test
    public void test() {
        String source = "1234567890-qwertyuiop";
        //获取base64格式的key
        String macKey = HmacUtils.createMacKey();
        System.out.println(macKey);
        //用key生成安全码
        byte[] hmac = HmacUtils.hmac(macKey, source);
        String s = Base64.encodeBytes(hmac);
        byte[] hmac2 = HmacUtils.hmac(macKey, source);
        String s2 = Base64.encodeBytes(hmac2);
        byte[] hmac3 = HmacUtils.hmac("ortherkey", source);
        String s3= Base64.encodeBytes(hmac3);
        System.out.println(s);
        System.out.println(s2);
        System.out.println(s3);
        assert s.equals(s2) ;
        assert !s.equals(s3) ;
    }
}
