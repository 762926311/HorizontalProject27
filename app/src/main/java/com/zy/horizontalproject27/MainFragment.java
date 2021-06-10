package com.zy.horizontalproject27;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainFragment extends Fragment {

    public TextView fg_user_permission_gun_state = null;
    public TextView fg_user_permission_value_money = null;
    public TextView fg_user_permission_oil_state = null;
    public TextView fg_user_permission_money_state = null;
    public TextView fg_user_real_money = null;
    public TextView fg_user_permission_price = null;
    public TextView fg_user_balance = null;
    public TextView fg_user_permission_user_id = null;
    public TextView fg_user_permission_integral = null;
    public TextView fg_user_permission_tip = null;
    public Button fg_user_permission_ok = null;
    public TextView fg_user_discount_mode = null;
    private TextView fg_user_pay_type;
    private TextView fg_user_discount_money = null;

    public LinearLayout ll_user_permission_user_id = null;
    public LinearLayout ll_user_real_receive_money = null;
    public LinearLayout ll_user_balance=null;
    public LinearLayout ll_user_discount_money = null;

    public int udpAddOilPayType = 0;
    public boolean isOneAddOilEnd = false;
    public boolean isTwoAddOilEnd = false;
    public boolean isIntegral = false;//是否可以积分

    public static boolean breceiveDatapannel1 = false;
    public static boolean breceiveDatapannel2 = false;

    private PermissionDownTimer mPermissionDownTimer;

    class PermissionDownTimer extends CountDownTimer {
        public PermissionDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            final long time = millisUntilFinished / 1000L;
             activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time > 0) {
                        fg_user_permission_tip.setText("" + time);
                        if(time==1){
                            timeOutToBack();
                        }
                    } else {
                        timeOutToBack();
                    }
                }
            });
        }
        @Override
        public void onFinish() {
        }
    }

    private void timeOutToBack() {
        MainActivity.openFragementOne = false;
        MainActivity.openFragementTwo = false;
        if (fg_user_permission_ok.getVisibility() == View.VISIBLE) {
            fg_user_permission_ok.performClick();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.mainfragment, null);
        getDataFromOther();
        findView(view);
        return view;
    }

    Activity activity = null;
    MainActivity mMyMainActivity = null;
    public void findView(View v){
        ll_user_permission_user_id = v.findViewById(R.id.ll_user_permission_user_id);
        fg_user_permission_gun_state = v.findViewById(R.id.fg_user_permission_gun_state);
        fg_user_permission_value_money = v.findViewById(R.id.fg_user_permission_value_money);
        fg_user_permission_oil_state = v.findViewById(R.id.fg_user_permission_oil_state);
        fg_user_permission_money_state = v.findViewById(R.id.fg_user_permission_money_state);
        fg_user_real_money = v.findViewById(R.id.fg_user_real_money);
        fg_user_permission_ok = v.findViewById(R.id.fg_user_permission_ok);
        fg_user_permission_price = v.findViewById(R.id.fg_user_permission_price);
        fg_user_balance = v.findViewById(R.id.fg_user_balance);
        fg_user_permission_user_id = v.findViewById(R.id.fg_user_permission_user_id);
        fg_user_permission_integral = v.findViewById(R.id.fg_user_permission_integral);
        fg_user_permission_tip = v.findViewById(R.id.fg_user_permission_tip);
        fg_user_pay_type = v.findViewById(R.id.fg_user_pay_type);
        fg_user_discount_money = v.findViewById(R.id.fg_user_discount_money);

        fg_user_discount_mode = v.findViewById(R.id.fg_user_permission_tip);
        ll_user_real_receive_money = v.findViewById(R.id.ll_user_real_receive_money);
        ll_user_balance=v.findViewById(R.id.ll_user_balance);
        ll_user_discount_money=v.findViewById(R.id.ll_user_discount_money);

        activity = getActivity();
        mMyMainActivity = (MainActivity) activity;

        fg_user_permission_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTimer();
                switch (faceType) {
                    case Common.GUN_TYPE_ONE:
                        mMyMainActivity.showFragment(10);
                        break;
                    case Common.GUN_TYPE_TWO:
                        mMyMainActivity.showFragment(20);
                        break;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public int oil_one_gun_number,oil_two_gun_number,faceType;
    public int oil_gun_number,pay_type;

    public void getDataFromOther(){
        Bundle arguments = getArguments();
        if (arguments != null) {
            faceType = arguments.getInt(Common.GUN_TYPE, 1);
            oil_one_gun_number = arguments.getInt(Common.GUN_TYPE_ONE_NUMBER, 0);
            oil_two_gun_number = arguments.getInt(Common.GUN_TYPE_TWO_NUMBER, 0);
            pay_type = arguments.getInt(Common.PAY_TYPE,4);
            if(faceType==1){
                oil_gun_number = oil_one_gun_number;
            }else if(faceType==2){
                oil_gun_number = oil_two_gun_number;
            }
        }
    }

    private void cancelTimer() {
        if(oil_gun_number == oil_one_gun_number){
            breceiveDatapannel1 = false;
        }
        if(oil_gun_number == oil_two_gun_number){
            breceiveDatapannel2 = false;
        }
        if (mPermissionDownTimer != null) {
            mPermissionDownTimer.cancel();
            mPermissionDownTimer = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGunAddOilStatus(final GunAddOilStatusInfo gunAddOilStatusInfo){
        if(gunAddOilStatusInfo != null && gunAddOilStatusInfo.getMessageType() != 11){
            return;
        }
        udpAddOilPayType = gunAddOilStatusInfo.getReturnPayType();
        isIntegral = (gunAddOilStatusInfo.getIntegral()==0)?false:true;
        System.out.println("yanyan getGunAddOilStatus UserLoginFragement="+gunAddOilStatusInfo.getNpanel()+","+gunAddOilStatusInfo.getMoneyCount()+",payType="+udpAddOilPayType);
        //判断是否是面板1，面板1是否加油完成
        if(gunAddOilStatusInfo.getNpanel()==oil_one_gun_number && !isOneAddOilEnd ){
            breceiveDatapannel1 = true;
        }
        //判断是否是面板2，面板2是否加油完成，是否正在人脸识别
        if(gunAddOilStatusInfo.getNpanel()==oil_two_gun_number && !isTwoAddOilEnd ){
            breceiveDatapannel2 = true;
        }

        //如果不是同一个面板的数据，可以不接收处理
        if(gunAddOilStatusInfo.getNpanel()!= oil_gun_number){
            return;
        }
//       if(gunAddOilStatusInfo.getGunStaus()==0){
//          timeOutToBack();
//       }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //fg_user_permission_value_money.setText(user_money);
                //fg_user_permission_value_money.setText(Utils.toDoubleNumber(addoilMoney/100)+"元");
                fg_user_permission_oil_state.setText(String.valueOf(gunAddOilStatusInfo.oilCount)+"升");
                fg_user_permission_money_state.setText(String.valueOf(gunAddOilStatusInfo.moneyCount)+"元");
                fg_user_permission_price.setText(String.valueOf(gunAddOilStatusInfo.priceCount)+"元");
                fg_user_balance.setText(String.valueOf(gunAddOilStatusInfo.balance));
                payType(pay_type,fg_user_pay_type);

                if(gunAddOilStatusInfo.returnPayType!=Common.MONEY_PAY){
                    ll_user_permission_user_id.setVisibility(View.VISIBLE);
                    //fg_user_permission_user_id.setText(usrId);
                }else{
                    ll_user_permission_user_id.setVisibility(View.GONE);
                }
//
//                if(isShowContent==1){
//                    ll_user_permission_car_number.setVisibility(View.VISIBLE);
//                    fg_user_car_number.setText(carNumber);
//                }else{
//                    ll_user_permission_car_number.setVisibility(View.GONE);
//                }

                switch (gunAddOilStatusInfo.getGunStaus()) {
                    case 0:
                        fg_user_permission_gun_state.setText("请提枪加油(U)");
                        break;
                    case 1:
                        fg_user_permission_gun_state.setText("加油中(U)");
                        break;
                    case 2:
                        fg_user_permission_gun_state.setText("抬枪");
                        break;
                    case 3:
                        fg_user_permission_gun_state.setText("加油结束");
                        String strUnit="";
                        switch (gunAddOilStatusInfo.unit){
                            case 1:
                                strUnit = "元";
                                break;
                            case 2:
                                strUnit = "升";
                                break;
                            case 3:
                                strUnit = "点";
                                break;
                            default:
                                strUnit = "";
                                break;
                        }
                        /////////////////////////////新增优惠块代码//////////////////////////////////////
                        discountShow(gunAddOilStatusInfo.returnPayType,gunAddOilStatusInfo.discountMode,gunAddOilStatusInfo.discountCount,gunAddOilStatusInfo.realCount,gunAddOilStatusInfo.balance,strUnit);
                        //////////////////////////////////////////////////////////////////////////
                        if(oil_gun_number == oil_one_gun_number){
                            MainActivity.enterMainOnePannel=0;
                            MainActivity.one_udp_isclick = true;
                            VideoActivity.enterOnePannel = 0;
                            VideoActivity.videoOneUdp = true;
                            breceiveDatapannel1 = false;
                            isOneAddOilEnd = true;
                        }
                        if(oil_gun_number == oil_two_gun_number){
                            MainActivity.enterMainTwoPannel=0;
                            MainActivity.two_udp_isclick = true;
                            VideoActivity.enterTwoPannel = 0;
                            VideoActivity.videoTwoUdp = true;
                            breceiveDatapannel2 = false;
                            isTwoAddOilEnd = true;
                        }

                            if (!breceiveDatapannel1 && !breceiveDatapannel2) {
                                if (EventBus.getDefault().isRegistered(MainFragment.this)) {
                                    EventBus.getDefault().unregister(MainFragment.this);
                                }
                            }


                        //udp加油是否要有积分的功能-->2020-1-8 UDP 新增加代码
                        fg_user_permission_ok.setVisibility(View.VISIBLE);
                        fg_user_permission_integral.setVisibility(View.GONE);
                        //gunAddOilStatusInfo.getReturnPayType()==1 这里是否要判断什么样的支付的类型
                        if(isIntegral)
                        {
                            fg_user_permission_integral.setVisibility(View.VISIBLE); //显示积分按钮
                        }
                        else
                        {
                            fg_user_permission_integral.setVisibility(View.GONE);   //隐藏积分按钮
                            if (mPermissionDownTimer == null) {
                                mPermissionDownTimer = new PermissionDownTimer((long) (10 * 1000), 1000);
                                mPermissionDownTimer.start();
                            }
                        }
                        break;
                }
            }
        });
    }

    public void payType(int pay_type,TextView textView) {
        switch (pay_type) {
            case Common.MONEY_PAY:
                textView.setText("现金");
                break;

            case Common.ICCARD_PAY:
                textView.setText("IC卡");
                break;
        }
    }


    public void discountShow(int paytype ,int discountMode,String disCountValue,String realValue,String balanaceValue,String strUnit){
        if(paytype == Common.MONEY_PAY){
            ll_user_real_receive_money.setVisibility(View.GONE);
            ll_user_balance.setVisibility(View.GONE);
            ll_user_discount_money.setVisibility(View.GONE);
        }else{
            if(discountMode== 0){
                ll_user_real_receive_money.setVisibility(View.GONE);
                ll_user_discount_money.setVisibility(View.GONE);
                ll_user_balance.setVisibility(View.VISIBLE);
            }else {
                ll_user_real_receive_money.setVisibility(View.VISIBLE);
                ll_user_balance.setVisibility(View.VISIBLE);
                ll_user_discount_money.setVisibility(View.VISIBLE);
            }
        }
        if(discountMode ==0){
            fg_user_discount_mode.setText("无优惠");
            fg_user_balance.setText(balanaceValue+strUnit);
        }else if(discountMode == 1){
            fg_user_discount_mode.setText("金额优惠");
            fg_user_discount_money.setText(disCountValue+"元");
            fg_user_real_money.setText(realValue+"元");
            fg_user_balance.setText(balanaceValue+"元");
        }else if (discountMode == 2){
            fg_user_discount_mode.setText("油量优惠");
            fg_user_discount_money.setText(disCountValue+"升");
            fg_user_real_money.setText(realValue+"升");
            fg_user_balance.setText(balanaceValue+"升");
        }
    }

}
