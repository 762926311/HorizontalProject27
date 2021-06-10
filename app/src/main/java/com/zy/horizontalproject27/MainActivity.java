package com.zy.horizontalproject27;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.fragment.app.FragmentActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends FragmentActivity {
    public static boolean openFragementOne = false;
    public static boolean openFragementTwo = false;

    public EditText edit_gun_no_one,edit_gun_no_two = null;
    public EditText edit_oil_name_one,edit_oil_name_two = null;
    public String str_gun_no_one,str_gun_no_two,str_oil_name_one,str_oil_name_two = "";

    public static final String STRPREFERENCE = "strPreference";
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;
    public String oil_gun_no_one="1",oil_gun_no_two="2";
    public String oil_name_one="92#",oil_name_two="92#";

    public PopupWindow colorPopupWindow = null;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        EventBus.getDefault().register(this);

        edit_gun_no_one = findViewById(R.id.edit_gun_no_one);
        edit_gun_no_two = findViewById(R.id.edit_gun_no_two);
        edit_oil_name_one = findViewById(R.id.edit_oil_name_one);
        edit_oil_name_two = findViewById(R.id.edit_oil_name_two);




        edit_gun_no_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                str_gun_no_one = editable.toString();
                editor.putString("onegunno",str_gun_no_one);
                editor.commit();
            }
        });

        edit_gun_no_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                str_gun_no_two = editable.toString();
                editor.putString("twogunno",str_gun_no_two);
                editor.commit();
            }
        });


        edit_oil_name_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                str_oil_name_one = editable.toString();
                editor.putString("oneoilname",str_oil_name_one);
                editor.commit();
            }
        });

        edit_oil_name_two.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                str_oil_name_two = editable.toString();
                editor.putString("twooilname",str_oil_name_two);
                editor.commit();
            }
        });

        edit_oil_name_one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edit_oil_name_one.getCompoundDrawables();
                Drawable drawable = edit_oil_name_one.getCompoundDrawables()[2];
                if (drawable == null) return false;
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                System.out.println("yanyan width="+event.getX()+",different="+(edit_oil_name_one.getWidth() - edit_oil_name_one.getPaddingRight() - drawable.getIntrinsicWidth()));
                if (event.getX() > edit_oil_name_one.getWidth() - edit_oil_name_one.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    Drawable null_placeholder = getResources().getDrawable(R.mipmap.null_placeholder);
                    null_placeholder.setBounds(0, 0, null_placeholder.getMinimumWidth(),null_placeholder.getMinimumHeight());


                    int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                    int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
                    LinearLayout layout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.color_popwindow, null);
                    Button btn_red_color = layout.findViewById(R.id.btn_red_color);
                    btn_red_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_one.setTextColor(Color.RED);
                            editor.putInt("oneNameColor",Color.RED);
                            colorPopupWindow.dismiss();
                        }
                    });

                    Button btn_blue_color = layout.findViewById(R.id.btn_blue_color);
                    btn_blue_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_one.setTextColor(Color.BLUE);
                            editor.putInt("oneNameColor",Color.BLUE);
                            colorPopupWindow.dismiss();
                        }
                    });

                    Button btn_green_color = layout.findViewById(R.id.btn_green_color);
                    btn_green_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_one.setTextColor(Color.GREEN);
                            editor.putInt("oneNameColor",Color.GREEN);
                            colorPopupWindow.dismiss();
                        }
                    });

                    Button btn_yellow_color = layout.findViewById(R.id.btn_yellow_color);
                    btn_yellow_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_one.setTextColor(Color.YELLOW);
                            editor.putInt("oneNameColor",Color.YELLOW);
                            colorPopupWindow.dismiss();
                        }
                    });

                    colorPopupWindow = new PopupWindow(layout,screenWidth/2,screenHeight/3);
                    colorPopupWindow.showAsDropDown(edit_oil_name_one, 0, 0);
                    System.out.println("请下拉一个popupWindow");
                }
                return false;
            }
        });

        edit_oil_name_two.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edit_oil_name_two.getCompoundDrawables();
                Drawable drawable = edit_oil_name_two.getCompoundDrawables()[2];
                if (drawable == null) return false;
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                System.out.println("yanyan width="+event.getX()+",different="+(edit_oil_name_two.getWidth() - edit_oil_name_two.getPaddingRight() - drawable.getIntrinsicWidth()));
                if (event.getX() > edit_oil_name_two.getWidth() - edit_oil_name_two.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    Drawable null_placeholder = getResources().getDrawable(R.mipmap.null_placeholder);
                    null_placeholder.setBounds(0, 0, null_placeholder.getMinimumWidth(),null_placeholder.getMinimumHeight());


                    int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
                    int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
                    LinearLayout layout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.color_popwindow, null);
                    Button btn_red_color = layout.findViewById(R.id.btn_red_color);
                    btn_red_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_two.setTextColor(Color.RED);
                            editor.putInt("twoNameColor",Color.RED);
                            colorPopupWindow.dismiss();
                        }
                    });

                    Button btn_blue_color = layout.findViewById(R.id.btn_blue_color);
                    btn_blue_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_two.setTextColor(Color.BLUE);
                            editor.putInt("twoNameColor",Color.BLUE);
                            colorPopupWindow.dismiss();
                        }
                    });

                    Button btn_green_color = layout.findViewById(R.id.btn_green_color);
                    btn_green_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_two.setTextColor(Color.GREEN);
                            editor.putInt("twoNameColor",Color.GREEN);
                            colorPopupWindow.dismiss();
                        }
                    });

                    Button btn_yellow_color = layout.findViewById(R.id.btn_yellow_color);
                    btn_yellow_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edit_oil_name_two.setTextColor(Color.YELLOW);
                            editor.putInt("twoNameColor",Color.YELLOW);
                            colorPopupWindow.dismiss();
                        }
                    });

                    colorPopupWindow = new PopupWindow(layout,screenWidth/2,screenHeight/3);
                    colorPopupWindow.showAsDropDown(edit_oil_name_two, 0, 0);
                }
                return false;
            }
        });


        showFragment(ONE_LIST_MAIN_PAGE);
        showFragment(TWO_LIST_MAIN_PAGE);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onStart() {
        sharedPreferences = getSharedPreferences(STRPREFERENCE,MODE_APPEND);
        editor = sharedPreferences.edit();
        if(sharedPreferences.getString("onegunno","1")!=""){
            oil_gun_no_one =  sharedPreferences.getString("onegunno","1");
            oil_name_one =  sharedPreferences.getString("oneoilname","92#");
        }
        if(sharedPreferences.getString("twogunno","2")!=""){
            oil_gun_no_two =  sharedPreferences.getString("twogunno","2");
            oil_name_two =  sharedPreferences.getString("twooilname","92#");
        }

        if(sharedPreferences.getInt("oneNameColor",Color.RED) != 0){
            int oneColor = sharedPreferences.getInt("oneNameColor",Color.RED);
            edit_oil_name_one.setTextColor(oneColor);
        }

        if(sharedPreferences.getInt("twoNameColor",Color.RED) != 0){
            int twoColor = sharedPreferences.getInt("twoNameColor",Color.RED);
            edit_oil_name_two.setTextColor(twoColor);
        }

        edit_gun_no_one.setText(oil_gun_no_one+"");
        edit_gun_no_two.setText(oil_gun_no_two+"");
        edit_oil_name_one.setText(oil_name_one);
        edit_oil_name_two.setText(oil_name_two);
        super.onStart();
    }

    public static int enterMainOnePannel,enterMainTwoPannel = 0;
    public static boolean one_udp_isclick = true;
    public static boolean two_udp_isclick = true;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGunAddOilStatus(GunAddOilStatusInfo gunAddOilStatusInfo) {
        if (gunAddOilStatusInfo.getGunStaus() == 3) return;
        if ((gunAddOilStatusInfo.getMessageType() == 11 || !VideoActivity.videoOneUdp) && gunAddOilStatusInfo.getNpanel() == Integer.parseInt(oil_gun_no_one) && !MainFragment.breceiveDatapannel1)
            enterMainOnePannel++;
        if (enterMainOnePannel == 1 && !MainFragment.breceiveDatapannel1) {
            one_udp_isclick = false;
            VideoActivity.videoOneUdp = true;
            Bundle bundle1 = new Bundle();
            bundle1.putInt(Common.GUN_TYPE, Common.GUN_TYPE_ONE);
            bundle1.putInt(Common.GUN_TYPE_ONE_NUMBER, Integer.parseInt(oil_gun_no_one));
            bundle1.putInt(Common.PAY_TYPE, gunAddOilStatusInfo.getReturnPayType());
            MainFragment mCardFragment = new MainFragment();
            mCardFragment.setArguments(bundle1);
            getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_one, mCardFragment).commitAllowingStateLoss();
        }

     if ((gunAddOilStatusInfo.getMessageType() == 11 || !VideoActivity.videoTwoUdp) && gunAddOilStatusInfo.getNpanel() == Integer.parseInt(oil_gun_no_two) && !MainFragment.breceiveDatapannel2){
                 enterMainTwoPannel++;
                if (enterMainTwoPannel == 1 && !MainFragment.breceiveDatapannel2) {
                    two_udp_isclick = false;
                    VideoActivity.videoTwoUdp = true;
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(Common.GUN_TYPE, Common.GUN_TYPE_TWO);
                    bundle2.putInt(Common.GUN_TYPE_TWO_NUMBER, Integer.parseInt(oil_gun_no_two));
                    bundle2.putInt(Common.PAY_TYPE, gunAddOilStatusInfo.getReturnPayType());
                    MainFragment mCardFragment = new MainFragment();
                    mCardFragment.setArguments(bundle2);
                    getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_two, mCardFragment).commitAllowingStateLoss();
                }
        }
    }

    BackVideoTimer backVideoTimer = null;

    class BackVideoTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public BackVideoTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            final long time = millisUntilFinished / 1000L;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time > 0) {
                        if(time == 1) {
                            cancelTimer();
                            if(one_udp_isclick &&  two_udp_isclick) {
                                finish();
                            }
                        }
                    } else {
                        cancelTimer();
                        if(one_udp_isclick && two_udp_isclick) {
                            finish();
                        }
                    }
                }
            });
        }
        @Override
        public void onFinish() {
        }
    }

    private void cancelTimer() {
        if(backVideoTimer != null) {
            backVideoTimer.cancel();
            backVideoTimer = null;
        }
    }

    public static final int ONE_LIST_MAIN_PAGE = 10;
    public static final int TWO_LIST_MAIN_PAGE = 20;
    //显示列表各页面调用
    public  void showFragment(int which) {
        switch (which) {
            case ONE_LIST_MAIN_PAGE://列表1主界面
                videoBackMainPage();
                Bundle bundle1 = new Bundle();
                bundle1.putInt(Common.GUN_TYPE, Common.GUN_TYPE_ONE);
                bundle1.putInt(Common.GUN_TYPE_ONE_NUMBER,Integer.parseInt(oil_gun_no_one));
                BasePayFagment mBaseOnePayFagment = new BasePayFagment();
                mBaseOnePayFagment.setArguments(bundle1);
                getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_one, mBaseOnePayFagment).commitAllowingStateLoss();
                break;

            case TWO_LIST_MAIN_PAGE:
                videoBackMainPage();
                Bundle bundle2 = new Bundle();
                bundle2.putInt(Common.GUN_TYPE, Common.GUN_TYPE_TWO);
                bundle2.putInt(Common.GUN_TYPE_TWO_NUMBER,Integer.parseInt(oil_gun_no_two));
                BasePayFagment mBaseTwoPayFagment = new BasePayFagment();
                mBaseTwoPayFagment.setArguments(bundle2);
                getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_two, mBaseTwoPayFagment).commitAllowingStateLoss();
                break;
        }
    }

    /**
     *视频页面返回主页面,时间定为100S,1分40秒
     */
    public void videoBackMainPage(){
        if (backVideoTimer == null) {
            backVideoTimer = new BackVideoTimer((long)(30 * 1000),1000);
            backVideoTimer.start();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        cancelTimer();
        super.onDestroy();
    }
}