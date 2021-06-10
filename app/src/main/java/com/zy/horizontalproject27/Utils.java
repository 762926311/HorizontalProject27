package com.zy.horizontalproject27;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private final static String TAG = Utils.class.getSimpleName();
    public static String mpath="";
    /**
     * 转换socket传输给服务器的数据
     */
    public static byte[] hexStrToBinaryStr(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return null;
        }
        if (hexString.length() % 2 != 0) {
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
        return bytes;
    }

    /**
     * byte数组转换成16进制
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 把16进制字符串转换成字节数组
     * @param hex
     * @return
     */

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)  {
                sbu.append((int)chars[i]);
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * 转换socket服务器返回值的数据
     */
    public static String hexString2binaryString(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return null;
        }
        String binaryString = "";
        for (int i = 0; i < hexString.length(); i++) {
            //截取hexStr的一位
            String hex = hexString.substring(i, i + 1);
            //通过toBinaryString将十六进制转为二进制
            String binary = Integer.toBinaryString(Integer.parseInt(hex, 16));
            //因为高位0会被舍弃，先补上4个0
            String tmp = "0000" + binary;
            //取最后4位，将多补的0去掉
            binaryString += tmp.substring(tmp.length() - 4);
        }
        return binaryString;
    }

    public static boolean getBit(int num, int i)
    {
        return ((num & (1 << i)) != 0);//true 表示第i位为1,否则为0
    }

    public static String toStringHex(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * socket数据转string
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes) {
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }

    /**
     * 获取外部SD卡路径
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.toString();
        } else {
            return ""; //没有读取到sdCard
        }
    }

    /**
     * 隐藏底部虚拟按键
     */
    public void hideBottomUIMenu(Activity ac) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = ac.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = ac.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static boolean isAllNum(String str) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    //调用隐藏系统默认的输入法
    public static void showOrHide(Context context, Activity activity) {
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();
            if (windowToken != null) {
                ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static String toDoubleNumber(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }


    /**
     * 获取某一个文件下的所有的文件名
     */
    public static List<String> getFilesAllName(Context ctx, String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> s = new ArrayList<>();
        if(files!=null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(".mp4") ||
                        files[i].getName().contains(".wav") ||
                        files[i].getName().contains(".avi") ||
                        files[i].getName().contains(".3gp")) {
                    s.add(files[i].getName());
                }
            }
        }
        else{
           // Toast.makeText(ctx, "没有文件", Toast.LENGTH_SHORT).show();
        }
        return s;
    }

    public static char szstr2bin[] = {
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,            0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,            0,1,2,3,4,5,6,7,8,9,10,0,0,0,0,0,
                   0,10,11,12,13,14,15,0,0,0,0,0,0,0,0,0,      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                   0,10,11,12,13,14,15,0,0,0,0,0,0,0,0,0
                 };

     public static byte[] str2byte(String str) {
           int length = str.length();
           if(length < 1)  return null;
           if(length%2 != 0)return null;
           byte[] result = new byte[str.length()/2];
           for (int i = 0;i<length;) {
               char H = szstr2bin[str.charAt(i++)&0xFF];
               char L = szstr2bin[str.charAt(i++)&0xFF];
               result[(i/2)-1] = (byte)(H*16 + L);
           }
           return result;
    }

}
