package com.zy.horizontalproject27;

import android.annotation.SuppressLint;
import android.util.Log;


public class Common {
    private static final String TAG = Common.class.getSimpleName();
    /**
     * +
     * 备注：看Utils.getSpData  五类变量均为动态手动设置
     * 0    列表油品1
     * 1    列表油品2
     * 2    MP4地址
     * 3    IP地址
     * 4    端口号
     * <p>
     * 面板号：列表1为0x01   列表2为0x02
     * 枪序号均固定：0x01
     */
    public final static int GUN_TYPE_ONE = 1;//面板1（左边列表为面板1  值0x01）
    public final static int GUN_TYPE_TWO = 2;//面板2（左边列表为面板2  值0x02）
    public final static int GUN_TYPE_THREE = 3; //面板3(左边列表为面板3 值0x03)
    public final static int GUN_TYPE_FOUR = 4;//面板4 (左边列表为面板4 值0x04)

    public final static String GUN_NUM = "0x01";//枪序号（固定）
    public final static String GUN_TYPE = "gun_type";
    public final static String GUN_TYPE_ONE_NUMBER="gun_type_one_number";
    public final static String GUN_TYPE_TWO_NUMBER="gun_type_two_number";
    public final static String GUN_TYPE_THREE_NUMBER="gun_type_three_number";
    public final static String GUN_TYPE_FOUR_NUMBER="gun_type_four_number";

    //数据包头FA55
    public final static String DATA_HEADER = "fa55";

    //帧号
    public final static String FRAME_NUMBER = "01";

    //枪序号（固定）
    public final static String OIL_GUN_PORT = "01";

    //有效数据长度
    public final static String REGISTER_VALID_DATA_LEN = "0007";//注册
    public final static String PASSWORD_VALID_DATA_LEN = "0008";//支付验证
    public final static String AUTHORIZED_VALID_DATA_LEN = "000d";//授权
    public final static String REAL_VALID_DATA_LEN = "0011";//加油
    public final static String ADD_OIL_END_LEN = "0009"; //加油结束
    public static String TERMINAL_REQUEST_RUN_LEN = "0019"; //终端请求运行验证
    public final static String TERMINAL_REQUEST_PARAM_END_LEN = "0005"; //终端接收参数回复

    public static String INTEGRAL_VALID_DATA_LEN = "";//积分验证请求的有效长度
    public static String INTEGRAL_VALID_CONFIRM_DATA_LEN = "";//积分确认请求的有效长度

    //请求命令
    public final static String REGISTER_COMMAND = "11";//-->用户注册命令
    public final static String PASSWORD_COMMAND = "13";//-->用户密码命令
    public final static String AUTHORIZED_COMMAND = "15";//-->授权加油命令
    public final static String REAL_COMMAND = "17";//-->加油数据命令
    public final static String DOWNLOAD_REQUEST_COMMAND = "21";
    public final static String DOWNLOAD_VALID_DATA_LEN = "000c";
    public final static String ADD_OIL_END_COMMAND="18"; //加油结束回复后台的命令
    public final static String TERMINAL_REQUEST_PARAM_END_COMMAND = "24";//接收完参数后回复后台的命令
    public final static String TERMINAL_REQUEST_RUN_COMMAND = "30"; //终端运行

    public final static String INTEGRAL_VERFICATION_COMMAND = "32"; //积分验证请求
    public final static String INTEGRAL_CONFIRM_COMMAND = "34"; //积分确认请求

    //校验
    public final static String REGISTER_DATA_CHECK = "b5";//-->b5 数据校验
    public final static String PASSWORD_DATA_CHECK = "1a";//-->b5 数据校验

    //支付方式
    public final static String PAY_MONEY = "pay_money";
    public final static String PAY_IC = "pay_ic";
    public final static String PAY_RFID = "pay_rfid";
    public final static String PAY_FACE = "pay_face";

    //支付方式常量
    public final static int MONEY_PAY = 0;
    public final static int ICCARD_PAY = 1;
    public final static int WECHAT_PAY = 2;
    public final static int ALI_PAY = 3;
    public final static int FACE_PAY = 4;
    public final static int RFID_PAY = 5;
    public final static int QRCODE_PAY = 6;
    public final static int PWD_PAY = 7;
    public final static int FINGER_PAY = 8;
    public final static int OTHER_PAY = 9;

    public final static String PAY_TYPE = "pay_type";
    public final static String RFID_CONNTECT_TYPE = "rfid_connect_type";
    public final static String RFID_IP = "rfid_ip";
    public final static String RFID_PORT = "rfid_port";
    public final static String TOTAL_PANNEL_NUM = "total_pannel_num";

    public final static String TCP_IP = "tcp_ip";
    public final static String TCP_PORT = "tcp_port";
    public final static String UDP_PORT = "udp_port";
    public final static String COMPANY_NAME = "company_name";

    /**
     * 注册使用
     *
     * @param userID 客户ID号
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getRegisterHexStr(int userID) {
        String sPanel = String.format("%02x", 01); //面板
        Log.e("fy", sPanel);
        String sUserID = String.format("%08x", userID);//客户ID
        Log.e(TAG, "fy getHexStr sUserID >>> " + sUserID);
        //            包头              帧号          长度                  面板         枪口          命令            ID                校正
        String s = DATA_HEADER + FRAME_NUMBER + REGISTER_VALID_DATA_LEN + sPanel + OIL_GUN_PORT + REGISTER_COMMAND + sUserID + REGISTER_DATA_CHECK;
        return s;
    }

    /**
     * 支付方式验证请求
     * 请求识别ID的用户密码
     */
    public static String getPwdHexStr(int mPanel, int userID, int payType) {
        //String sPanel =(( mPanel == 1) ? "01" : "02");
        String sPanel =  String.format("%02x", mPanel);
        String sUserID = String.format("%08x", userID);//客户ID
        String sPayType =  String.format("%02x", payType);
        //          包头              帧号          长度                  面板         枪口          命令            ID         支付方式       校验
        String s = DATA_HEADER + FRAME_NUMBER + PASSWORD_VALID_DATA_LEN + sPanel + OIL_GUN_PORT + PASSWORD_COMMAND +sPayType+ sUserID + PASSWORD_DATA_CHECK;
        return s;
    }

    /**
     * 积分验证请求
     */
    public static String getIntegralHexStr(int mPanel, int integralIdType, String integralId){
        String sPanel = String.format("%02x",mPanel);
        String sIntegralIdType = String.format("%02x",integralIdType);
        integralId = StringUtils.convertStringToHex(integralId.toUpperCase());
        String sIntegralLength = String.format("%02x",integralId.length()/2);
        int totalLen = 5 + integralId.length()/2; //前面9个字节是固定的，最后的终端的ID长度可变
        if(totalLen<16){
            //INTEGRAL_VALID_DATA_LEN = "000" + totalLen;
            INTEGRAL_VALID_DATA_LEN = "000" + intToHex(totalLen);
        }else {
            //INTEGRAL_VALID_DATA_LEN = "00" + totalLen;
            INTEGRAL_VALID_DATA_LEN = "00"+intToHex(totalLen);
        }
        String s = DATA_HEADER + FRAME_NUMBER + INTEGRAL_VALID_DATA_LEN + sPanel + OIL_GUN_PORT + INTEGRAL_VERFICATION_COMMAND +sIntegralIdType+ sIntegralLength + integralId + PASSWORD_DATA_CHECK;
        System.out.println("yanyan getIntegralHexStr send="+s+",INTEGRAL_VALID_DATA_LEN="+INTEGRAL_VALID_DATA_LEN+",integralId="+integralId);
        return s;
    }

    /**
     * 积分确认请求
     */
    public static String getIntegralConfirmHexStr(int mPanel, int integralIdType, String integralId, int integralValue, String serialNum){
        String sPanel = String.format("%02x",mPanel);
        String sIntegralIdType = String.format("%02x",integralIdType);
        integralId = StringUtils.convertStringToHex(integralId.toUpperCase());
        String sIntegralLength = String.format("%02x",integralId.length()/2);
        String sIntegralValue = String.format("%08x",integralValue);
       // String strSerialNum = String.format("%08x",serialNum);
        int totalLen = 13 + integralId.length()/2; //前面9个字节是固定的，最后的终端的ID长度可变
        //INTEGRAL_VALID_CONFIRM_DATA_LEN = "00" + totalLen;
        System.out.println("totalLen="+totalLen);
       // INTEGRAL_VALID_CONFIRM_DATA_LEN = ByteUtil.decimal2fitHex(totalLen);
        if(totalLen<16){
            //INTEGRAL_VALID_DATA_LEN = "000" + totalLen;
            INTEGRAL_VALID_CONFIRM_DATA_LEN= "000"+intToHex(totalLen);
        }else {
            //INTEGRAL_VALID_DATA_LEN = "00" + totalLen;
            INTEGRAL_VALID_CONFIRM_DATA_LEN= "00"+intToHex(totalLen);
        }
        System.out.println("yanyan getIntegralHexStr send integralValue="+integralValue+",serialNum="+serialNum+",slen="+ByteUtil.decimal2fitHex(totalLen));
        String s = DATA_HEADER + FRAME_NUMBER + INTEGRAL_VALID_CONFIRM_DATA_LEN + sPanel + OIL_GUN_PORT + INTEGRAL_CONFIRM_COMMAND+sIntegralIdType+ sIntegralLength + integralId +sIntegralValue + serialNum+ PASSWORD_DATA_CHECK;
        System.out.println("yanyan getIntegralHexStr send="+s+",INTEGRAL_VALID_CONFIRM_DATA_LEN="+INTEGRAL_VALID_CONFIRM_DATA_LEN);
        return s;
    }

    private static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        return a;
    }

    /**
     * 终端请求下载(操作人脸信息)
     */
    public static String getDownloadHexStr(int basicOperation, int handlerFaceNum, String lastTime){
        String sPanel = "01";
        String sBasicOperation = String.format("%02x",basicOperation);
        String sHandlerFaceNum = String.format("%02x",handlerFaceNum);
        // String sLastTime = String.format("%07x",lastTime);
        //          包头              帧号          长度                  面板         枪口          命令               校验            操作方式        处理人脸个数         最后时间
        String s = DATA_HEADER + FRAME_NUMBER + DOWNLOAD_VALID_DATA_LEN + sPanel + OIL_GUN_PORT + DOWNLOAD_REQUEST_COMMAND + sBasicOperation + sHandlerFaceNum + lastTime + PASSWORD_DATA_CHECK;
        return s;
    }

    /**
     * 人脸识别
     * 请求授权加油
     */
    public final static String PUSH_OIL_MONEY_TYPE = "02";//-->0x02：按金额加油
    public final static String PUSH_OIL_MOUNT_TYPE="01";//-->0x01：按油量加油

    public static String getAuthorizedHexStr(int mPanel, int userID, int moneyNum, int payType, int pushoilType) {
        String sPanel = String.format("%02x", mPanel); //面板
        String sUserID = String.format("%08x", userID);//客户ID
        String sMoneyNum = String.format("%08x", moneyNum);//客户需要加多少钱的油
        String sPayType =  String.format("%02x", payType);
        String sPushOilType =  String.format("%02x", pushoilType);
        System.out.println("sPushOilType="+sPushOilType+",sUserID="+sUserID);
        //包头           帧号           长度（以案例稳准）       面板      油枪          命令                                          支付方式          ID      加油方式        金额      校正
        String s = DATA_HEADER + FRAME_NUMBER + AUTHORIZED_VALID_DATA_LEN + sPanel + OIL_GUN_PORT + AUTHORIZED_COMMAND + sPayType + sUserID + sPushOilType + sMoneyNum + PASSWORD_DATA_CHECK;
        return s;
    }

    /**
     * UDP 加油结束后发回应给后台
     * @param mPanel
     * @param userID
     * @param payType
     * @return
     */
    public static String clientReplayAddOilEnd(int mPanel, int oilStatus, int userID, int payType){
        String soilStatus =  String.format("%02x", oilStatus);
        String sPanel =  String.format("%02x", mPanel);
        String sUserID = String.format("%08x", userID);//客户ID,4个字节
        String sPayType =  String.format("%02x", payType);
        //          包头              帧号          长度                  面板         枪口          命令            ID         支付方式       校验
        String s = DATA_HEADER + FRAME_NUMBER + ADD_OIL_END_LEN + sPanel + OIL_GUN_PORT + ADD_OIL_END_COMMAND +soilStatus+sPayType+ sUserID + PASSWORD_DATA_CHECK;
        return s;
    }

    /**
     * 终端接收完参数后回复后台的统一方法
     * @param mPanel
     * @param paramType
     * @return
     */
    public static String terminalReplayParamSettingEnd(int mPanel, int paramType, int result){
        String sPanel =  String.format("%02x", mPanel);
        String sParamType =  String.format("%02x", paramType);
        String sResult =  String.format("%02x", result);
        //          包头              帧号          长度                  面板         枪口          命令
        String s = DATA_HEADER + FRAME_NUMBER + TERMINAL_REQUEST_PARAM_END_LEN + sPanel + OIL_GUN_PORT+ TERMINAL_REQUEST_PARAM_END_COMMAND  + sParamType + sResult +  PASSWORD_DATA_CHECK;
        return s;
    }

    /**
     * 终端请求运行
     * @return
     */
    //请求操作 1   站点代码  4   终端设备编号长度 1  终端设备唯一编号 N
    public static String terminalRequestRun(int mPannel, int requestType, long lsiteCode, int nterminalLen, String terminalId){
        String sPanel = String.format("%02x",mPannel);
        String sRequestType = String.format("%02x",requestType);
        String siteCode = String.format("%08x",lsiteCode);
        String terminalLen = String.format("%02x",nterminalLen);
        //Y220YZZGDP
        terminalId = StringUtils.convertStringToHex(terminalId);
        //terminalId = Utils.stringToAscii((terminalId)); //转换成ASCII码,后再进行操作
        //System.out.println("yanyan terminalId 十进制="+terminalId);
        //89505048899090716880
        int totalLen = 9 + terminalId.length()/2; //前面9个字节是固定的，最后的终端的ID长度可变
        TERMINAL_REQUEST_RUN_LEN = "00" + totalLen;
        System.out.println("yanyan terminalRequestRun length="+TERMINAL_REQUEST_RUN_LEN+",totalLen="+totalLen);
        String s = DATA_HEADER + FRAME_NUMBER + TERMINAL_REQUEST_RUN_LEN + sPanel + OIL_GUN_PORT + TERMINAL_REQUEST_RUN_COMMAND + sRequestType + siteCode + terminalLen + terminalId + PASSWORD_DATA_CHECK;
        return s;
    }


}
