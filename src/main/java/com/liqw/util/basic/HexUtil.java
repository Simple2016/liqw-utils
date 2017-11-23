package com.liqw.util.basic;

/**
 * Created by liqw on 2017/11/23.
 */
public class HexUtil {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHexString(byte[] src) {
        char[] result = new char[src.length * 2];

        for (int i = 0; i < src.length; ++i) {
            result[i * 2] = DIGITS_LOWER[(src[i] & 240) >>> 4];
            result[i * 2 + 1] = DIGITS_LOWER[src[i] & 15];
        }

        return new String(result);
    }

    public static byte[] hexStringToBytes(String hexStr) {
        return hexCharsToBytes(hexStr.toLowerCase().toCharArray());
    }

    public static byte[] hexCharsToBytes(char[] hexChars) {
        if((hexChars.length & 1) == 1) {
            throw new RuntimeException("用于解析的十六进制字符数组的长度不对，不是2的整数倍");
        } else {
            int length = hexChars.length / 2;
            byte[] result = new byte[length];

            for(int i = 0; i < hexChars.length; i += 2) {
                int f = toDigit(hexChars[i]) << 4;
                f |= toDigit(hexChars[i + 1]);
                result[i >> 1] = (byte)f;
            }

            return result;
        }
    }

    private static int toDigit(char c) {
        for(int index = 0; index < 16; ++index) {
            if(DIGITS_LOWER[index] == c) {
                return index;
            }
        }

        throw new RuntimeException("字符" + c + "不是小写十六进制的字符");
    }
}
