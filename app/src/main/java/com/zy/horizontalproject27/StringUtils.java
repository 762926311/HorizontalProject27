package com.zy.horizontalproject27;
import android.text.TextUtils;

public class StringUtils {
    /**
     * 哈希byte数组转str
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }

    public static int hexToDecimal(String hex)
    {
        int decimalValue=0;
        for(int i=0;i<hex.length();i++)
        {
            char hexChar=hex.charAt(i);
            decimalValue=decimalValue*16+hexCharToDecimal(hexChar);
        }
        return decimalValue;
    }

    public static int hexCharToDecimal(char hexChar)
    {
        if(hexChar>='A'&&hexChar<='F')
            return 10+hexChar-'A';
        else
            return hexChar-'0';//切记不能写成int类型的0，因为字符'0'转换为int时值为48
    }

    /**
     * 字节数组转换成对应的16进制表示的字符串
     *
     * @param src
     * @return
     */
    public static String bytes2HexStr(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return "";
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            builder.append(buffer);
        }
        return builder.toString().toUpperCase();
    }

    public static String convertStringToHex(String str){
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }
        return hex.toString();
    }

    /**
     * 十六进制字节数组转字符串
     *
     * @param src 目标数组
     * @param dec 起始位置
     * @param length 长度
     * @return
     */
    public static String bytes2HexStr(byte[] src, int dec, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(src, dec, temp, 0, length);
        return bytes2HexStr(temp);
    }

    /**
     * str转哈希byte数组
     * @param hexString
     * @return
     */
    public static byte[] hexStrToBinaryStr(String hexString) {
       if (TextUtils.isEmpty(hexString)) {
            return null;
        }
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len / 2];
        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);
            index += 2;
        }
 /*     int m = 0, n = 0;
        int byteLen = hexString.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hexString.substring(i * 2, m) + hexString.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;*/
        return bytes;
    }
}
