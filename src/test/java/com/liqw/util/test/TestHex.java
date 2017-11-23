package com.liqw.util.test;

import com.liqw.util.basic.Base64;
import com.liqw.util.basic.HexUtil;
import com.liqw.util.basic.HmacUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by liqw on 2017/11/23.
 */
public class TestHex {
    @Test
    public void test() {
        byte[] bytes="HELO WORld".getBytes();
        String s1 = HexUtil.toHexString(bytes);
        System.out.println(s1);

        byte[] bytes1 = HexUtil.hexStringToBytes(s1);
        System.out.println(new String(bytes1));

        assert Arrays.equals(bytes, bytes1);

    }
}
