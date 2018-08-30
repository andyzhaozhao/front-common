package com.iandtop.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * byte操作工具类
 *
 * @author andyzhao
 */
public class BinaryUtil {

    private static char[] crcDictionary = {0xF078, 0xE1F1, 0xD36A, 0xC2E3, 0xB65C, 0xA7D5,
            0x954E, 0x84C7, 0x7C30, 0x6DB9, 0x5F22, 0x4EAB, 0x3A14, 0x2B9D,
            0x1906, 0x088F, 0xE0F9, 0xF170, 0xC3EB, 0xD262, 0xA6DD, 0xB754,
            0x85CF, 0x9446, 0x6CB1, 0x7D38, 0x4FA3, 0x5E2A, 0x2A95, 0x3B1C,
            0x0987, 0x180E, 0xD17A, 0xC0F3, 0xF268, 0xE3E1, 0x975E, 0x86D7,
            0xB44C, 0xA5C5, 0x5D32, 0x4CBB, 0x7E20, 0x6FA9, 0x1B16, 0x0A9F,
            0x3804, 0x298D, 0xC1FB, 0xD072, 0xE2E9, 0xF360, 0x87DF, 0x9656,
            0xA4CD, 0xB544, 0x4DB3, 0x5C3A, 0x6EA1, 0x7F28, 0x0B97, 0x1A1E,
            0x2885, 0x390C, 0xB27C, 0xA3F5, 0x916E, 0x80E7, 0xF458, 0xE5D1,
            0xD74A, 0xC6C3, 0x3E34, 0x2FBD, 0x1D26, 0x0CAF, 0x7810, 0x6999,
            0x5B02, 0x4A8B, 0xA2FD, 0xB374, 0x81EF, 0x9066, 0xE4D9, 0xF550,
            0xC7CB, 0xD642, 0x2EB5, 0x3F3C, 0x0DA7, 0x1C2E, 0x6891, 0x7918,
            0x4B83, 0x5A0A, 0x937E, 0x82F7, 0xB06C, 0xA1E5, 0xD55A, 0xC4D3,
            0xF648, 0xE7C1, 0x1F36, 0x0EBF, 0x3C24, 0x2DAD, 0x5912, 0x489B,
            0x7A00, 0x6B89, 0x83FF, 0x9276, 0xA0ED, 0xB164, 0xC5DB, 0xD452,
            0xE6C9, 0xF740, 0x0FB7, 0x1E3E, 0x2CA5, 0x3D2C, 0x4993, 0x581A,
            0x6A81, 0x7B08, 0x7470, 0x65F9, 0x5762, 0x46EB, 0x3254, 0x23DD,
            0x1146, 0x00CF, 0xF838, 0xE9B1, 0xDB2A, 0xCAA3, 0xBE1C, 0xAF95,
            0x9D0E, 0x8C87, 0x64F1, 0x7578, 0x47E3, 0x566A, 0x22D5, 0x335C,
            0x01C7, 0x104E, 0xE8B9, 0xF930, 0xCBAB, 0xDA22, 0xAE9D, 0xBF14,
            0x8D8F, 0x9C06, 0x5572, 0x44FB, 0x7660, 0x67E9, 0x1356, 0x02DF,
            0x3044, 0x21CD, 0xD93A, 0xC8B3, 0xFA28, 0xEBA1, 0x9F1E, 0x8E97,
            0xBC0C, 0xAD85, 0x45F3, 0x547A, 0x66E1, 0x7768, 0x03D7, 0x125E,
            0x20C5, 0x314C, 0xC9BB, 0xD832, 0xEAA9, 0xFB20, 0x8F9F, 0x9E16,
            0xAC8D, 0xBD04, 0x3674, 0x27FD, 0x1566, 0x04EF, 0x7050, 0x61D9,
            0x5342, 0x42CB, 0xBA3C, 0xABB5, 0x992E, 0x88A7, 0xFC18, 0xED91,
            0xDF0A, 0xCE83, 0x26F5, 0x377C, 0x05E7, 0x146E, 0x60D1, 0x7158,
            0x43C3, 0x524A, 0xAABD, 0xBB34, 0x89AF, 0x9826, 0xEC99, 0xFD10,
            0xCF8B, 0xDE02, 0x1776, 0x06FF, 0x3464, 0x25ED, 0x5152, 0x40DB,
            0x7240, 0x63C9, 0x9B3E, 0x8AB7, 0xB82C, 0xA9A5, 0xDD1A, 0xCC93,
            0xFE08, 0xEF81, 0x07F7, 0x167E, 0x24E5, 0x356C, 0x41D3, 0x505A,
            0x62C1, 0x7348, 0x8BBF, 0x9A36, 0xA8AD, 0xB924, 0xCD9B, 0xDC12,
            0xEE89, 0xFF00};

    public static byte[] getCRC16(byte[] bytes) {
        int crc = 0x0000;
        for (byte b : bytes) {
            crc = (crc >>> 8) ^ crcDictionary[(crc ^ b) & 0xff];
        }
        byte[] rr = intToByteLowInF(crc);
        return rr;
    }

    /**
     * 获取字符串的bcd编码
     *
     * @param asc
     * @return
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
            //System.out.format("%02X\n", bbt[p]);
        }
        return bbt;
    }

    /**
     * BCD码转为String
     *
     * @param bytes
     * @return
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * ascii码转bcd码
     * @param ascii
     * @param asc_len
     * @return
     */
    public static byte[] ascii2bcd(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
            // System.out.format("%02X\n", bcd[i]);
        }
        return bcd;
    }

    private static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，intToByteLowInF配套使用1
     *
     * @param src
     * @return
     */
    public static int byteToIntLowInF(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF) << 8)
                | ((src[2] & 0xFF) << 16)
                | ((src[3] & 0xFF) << 24));
        return value;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 byteToIntLowInF配套使用1
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToByteLowInF(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。intToByteHignInF配套使用2
     *
     * @param src
     * @return
     */
    public static int byteToIntHignInF(byte[] src) {
        int value;
        value = (int) (((src[0] & 0xFF) << 24)
                | ((src[1] & 0xFF) << 16)
                | ((src[2] & 0xFF) << 8)
                | (src[3] & 0xFF));
        return value;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序,byteToIntHignInF配套使用2
     *
     * @param value
     * @return
     */
    public static byte[] intToByteHignInF(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 一个byte取int数值，本方法适用于(低位在后，高位在前)的顺序.2
     */
    public static int oneByteToIntHignInF(byte b) {
        int value;
        byte[] src = new byte[]{0x00, 0x00, 0x00, b};
        value = (int) (((src[0] & 0xFF) << 24)
                | ((src[1] & 0xFF) << 16)
                | ((src[2] & 0xFF) << 8)
                | (src[3] & 0xFF));
        return value;
    }

    /**
     * 两个byte取int数值，本方法适用于(低位在后，高位在前)的顺序.2
     */
    public static int twoByteToIntHignInF(byte[] b) {
        int value;
        byte[] src = new byte[]{0x00, 0x00, b[0], b[1]};
        value = (int) (((src[0] & 0xFF) << 24)
                | ((src[1] & 0xFF) << 16)
                | ((src[2] & 0xFF) << 8)
                | (src[3] & 0xFF));
        return value;
    }

    /**
     * 将int数值转换为占八个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序,byteToIntHignInF配套使用2
     *
     * @param value
     * @return
     */
    public static byte[] longToByteHignInF(long value) {
        byte[] src = new byte[8];
        src[0] = (byte) ((value >> 56) & 0xFF);
        src[1] = (byte) ((value >> 48) & 0xFF);
        src[2] = (byte) ((value >> 40) & 0xFF);
        src[3] = (byte) ((value >> 32) & 0xFF);


        src[4] = (byte) ((value >> 24) & 0xFF);
        src[5] = (byte) ((value >> 16) & 0xFF);
        src[6] = (byte) ((value >> 8) & 0xFF);
        src[7] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * Ascii码获取字符串
     *
     * @param tBytes
     * @return
     */
    public static String ascIIToString(byte[] tBytes) {
        String nRcvString;
        StringBuffer tStringBuf = new StringBuffer();
        // byte[] tBytes=new byte[]{0x31,0x32,0x33};   //实际上是ascii码字符串"123"
        char[] tChars = new char[tBytes.length];

        for (int i = 0; i < tBytes.length; i++)
            tChars[i] = (char) tBytes[i];

        tStringBuf.append(tChars);
        nRcvString = tStringBuf.toString();          //nRcvString从tBytes转成了String类型的"123"
        return nRcvString;
    }

    /**
     * 获取字符串Ascii码
     *
     * @param nSndString
     * @return
     */
    public static byte[] stringToAscII(String nSndString) {
        byte[] tBytes = new byte[0];
        try {
            tBytes = nSndString.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return tBytes;
    }


    // 截取byte数组
    public static byte[] subArray(byte[] src, int from, int to) {
        int length = to - from;
        if (length > 0) {
            byte[] bbb = new byte[length];
            System.arraycopy(src, from, bbb, 0, to - from);
            return bbb;
        } else {
            return null;
        }
    }

    // byte数组后面追加byte数组
    public static byte[] appendByteArray(byte[] source, byte[] target) {
        int l = source.length + target.length;
        byte[] result = new byte[l];
        System.arraycopy(source, 0, result, 0, source.length);
        System.arraycopy(target, 0, result, source.length, target.length);

        return result;
    }

    // byte插入byte数组
    public static byte[] insertByteArray(byte[] source, int index, byte[] target) {
        int l = source.length + target.length;
        byte[] result = new byte[l];
        System.arraycopy(source, 0, result, 0, index);
        System.arraycopy(target, 0, result, index, target.length);
        System.arraycopy(source, index, result, index + target.length, source.length - index);

        return result;
    }

    //byte数组删除一位
    public static byte[] deleteByteArray(byte[] source, int index) {
        int l = source.length - 1;
        byte[] result = new byte[l];
        System.arraycopy(source, 0, result, 0, index);
        System.arraycopy(source, index + 1, result, index, l - index);

        return result;
    }

    /**
     * byte数组的集合，转化为一个byte数组
     *
     * @param bytebytes
     * @return
     */
    public static byte[] bytsArrayListTobyteArray(List<byte[]> bytebytes) {
        int size = 0;
        for (int i = 0; i < bytebytes.size(); i++) {
            size += bytebytes.get(i).length;
        }
        byte[] r = new byte[size];
        int pos = 0;
        for (int i = 0; i < bytebytes.size(); i++) {
            byte[] bytes = bytebytes.get(i);
            for (int m = 0; m < bytes.length; m++) {
                r[pos] = bytes[m];
                pos++;
            }
        }
        return r;
    }

    /**
     * 低位优先 转 高位优先
     *
     * @param
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] littleEndianToBigEndian(byte[] bs) {
        return new StringBuffer(new String(bs)).reverse().toString().getBytes();
    }

    /**
     * byte数组转哈希字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 将超过int数值范围的int 转为正数long
     * 如果超过范围为负值，则转换，否则直接返回
     * @param beyondMAXInt
     * @return
     */
    public static long longIntToLong(long beyondMAXInt) {
        if (beyondMAXInt < 0) {
            String hexStringValue = Long.toHexString(beyondMAXInt);
            beyondMAXInt = Long.parseLong(hexStringValue.substring(8), 16);
        }
        return beyondMAXInt;
    }
}
