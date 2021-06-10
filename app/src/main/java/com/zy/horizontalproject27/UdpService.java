package com.zy.horizontalproject27;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class UdpService extends Service {
    /**
     * UDP Socket相关的变量
     */
    private UdpReceiveThread mReceiveThread = null;
    public  DatagramSocket mSocket =null;
    public DatagramPacket datagramPacket = null;
    private InetAddress address = null;
    private String udp_address ="";
    private int udp_port = 9802;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        udpConnect();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        disConnectUdp();
        super.onDestroy();
    }


    public void udpConnect() {

        if (mSocket == null || mSocket.isClosed()) {
            try {
                address = InetAddress.getByName(udp_address);
                System.out.println("yanyan udpConnect address="+address);
                // TrafficStats.setThreadStatsTag(THREAD_ID);
                mSocket = new DatagramSocket(9801);
                //开启接收线程
                mReceiveThread = new UdpReceiveThread();
                mReceiveThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class UdpReceiveThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (mSocket == null || mSocket.isClosed())
                return;
            try {
                byte[] datas = new byte[1024];
                datagramPacket = new DatagramPacket(datas, datas.length);
                mSocket.receive(datagramPacket);
                byte[] result = datagramPacket.getData();
                //System.out.println("yanyan receiveMsg="+datagramPacket.getLength()+",对方IP="+datagramPacket.getAddress().getHostAddress()+",对方端口：="+datagramPacket.getPort());
                parseDownload(Utils.byteToHex(result).substring(0, datagramPacket.getLength() * 2));
                mHandler.sendEmptyMessage(2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseDownload(String data) {
        System.out.println("yanyan udpdata=" + data);
        String mCommonData = data.substring(14, 16);
        System.out.println("yanyan getGunAddOilStatus DownloadService mCommonData=" + mCommonData + "flag=" + (!TextUtils.isEmpty(mCommonData) && mCommonData.equalsIgnoreCase("17")));
        //yanyan udp add code 20190627
        if (!TextUtils.isEmpty(mCommonData) && mCommonData.equalsIgnoreCase("17")) {//判断命令是否是后台发送的加油命令
            int nPanel = Integer.parseInt(data.substring(10, 12), 16); //面板
            int returnPayType = Integer.parseInt(data.substring(26, 28)); //支付方式：0为现金支付，1为IC卡支付
            int haveIntegral = 0;
            System.out.println("yanyan getGunAddOilStatus DownloadService nPanel=" + nPanel + ",returnPayType=" + returnPayType);
            if (returnPayType == 0 || returnPayType == 1) {
                double i = Integer.parseInt(data.substring(28, 34), 16);
                double i1 = Integer.parseInt(data.substring(34, 40), 16);
                double i2 = Integer.parseInt(data.substring(40, 44), 16);
                //yanyan 20190710
                int discountMode = Integer.parseInt(data.substring(44, 46), 16);
                double realValue = Integer.parseInt(data.substring(46, 52), 16);
                double currentBalance = Integer.parseInt(data.substring(52, 60), 16);
                int unit = Integer.parseInt(data.substring(60, 62), 16);
                if (data.length() == 66) {
                    haveIntegral = Integer.parseInt(data.substring(62, 64), 16); //是否有积分功能
                }
                double oilData = i / 100;
                double moneyData = i1 / 100;
                double priceData = i2 / 100;
                //yanyan 20190710
                double realValueData = realValue / 100;
                double currentBalanceData = currentBalance / 100;
                double discountValueData = 0d;
                if (discountMode == 1) {
                    discountValueData = (moneyData - realValueData);
                } else if (discountMode == 2) {
                    discountValueData = (oilData - realValueData);
                }
                String oilCount = Utils.toDoubleNumber(oilData);
                String moneyCount = Utils.toDoubleNumber(moneyData);
                String priceCount = Utils.toDoubleNumber(priceData);
                //yanyan 20190710
                String realCount = Utils.toDoubleNumber(realValueData);
                String currentBalanceCount = Utils.toDoubleNumber(currentBalanceData);
                String strDisCountValue = Utils.toDoubleNumber(discountValueData);
                System.out.println("yanyan discountMode udp discountMode=" + discountMode + ",realCount=" + realCount + ",currentBalanceCount=" + currentBalanceCount + ",unit=" + unit + ",discount=" + discountValueData);

                int gunStatus = Integer.valueOf(data.substring(16, 18));
                int userId = Integer.parseInt(data.substring(18, 26), 16);
                System.out.println("yanyan getGunAddOilStatus oilCount =" + oilCount + ",moneyCount=" + moneyCount + ",priceCount=" + priceCount);
                //通过EventBus发送的MyMainActivity和MainFragment
                if(!MainActivity.one_udp_isclick ||  !MainActivity.two_udp_isclick){
                    EventBus.getDefault().post(new GunAddOilStatusInfo(11, nPanel, userId, gunStatus, returnPayType, oilCount, moneyCount, priceCount, discountMode, realCount, strDisCountValue, currentBalanceCount, unit, haveIntegral));
                }else{
                    EventBus.getDefault().post(new GunAddOilStatusInfo(10, nPanel, userId, gunStatus, returnPayType, oilCount, moneyCount, priceCount, discountMode, realCount, strDisCountValue, currentBalanceCount, unit, haveIntegral));
                }
                EventBus.getDefault().post(new GunAddOilStatusInfo(11, nPanel, userId, gunStatus, returnPayType, oilCount, moneyCount, priceCount, discountMode, realCount, strDisCountValue, currentBalanceCount, unit, haveIntegral));
                //如果加油结束，向后台发送加油结束的UDP命令
                if (gunStatus == 3) {
                    System.out.println("yanyan getGunAddOilStatus add Oil end");
                    requestAddOilEnd(nPanel, userId, returnPayType);
                }
            }
        }
    }

    //加油结束后的回复
    public void requestAddOilEnd(int mPanel,int userID,int payType){
        String a = Common.clientReplayAddOilEnd(mPanel,3,userID,payType);
        byte[] buf = StringUtils.hexStrToBinaryStr(a);
        DatagramPacket sendPacket = new DatagramPacket(buf,buf.length,address,udp_port);
        // 通过套接字发送数据：
        try {
            mSocket.send(sendPacket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        @ Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String userId = (String)msg.obj;
            if (msg.what == 2) {
                mReceiveThread.interrupt();
                mReceiveThread = null;
                mReceiveThread = new UdpReceiveThread();
                mReceiveThread.start();
            }
        }
    };


    public void disConnectUdp(){
        if(mSocket!=null){
            mSocket.close();
            mSocket = null;
        }

        if(mReceiveThread!=null){
            mReceiveThread.interrupt();
            mReceiveThread = null;
        }
    }
}
