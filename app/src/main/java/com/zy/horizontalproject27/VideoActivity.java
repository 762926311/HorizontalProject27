package com.zy.horizontalproject27;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.fragment.app.FragmentActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

public class VideoActivity extends FragmentActivity {
    private VideoView mVideoView = null;
   // private String mp4_path="/mnt/internal_sd/Records/英高宣传片.mp4";
    String mp4_path = "";
    Intent serviceIntent = null;
    String sdcardPath = "";
    public TextView tv_checksd,tv_tip = null;
    public static int enterOnePannel,enterTwoPannel=0;
    public static boolean videoOneUdp = true;
    public static boolean videoTwoUdp = true;
    String oil_one_gun_number="1",oil_two_gun_number = "2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);

        EventBus.getDefault().register(this);

        mp4_path = "android.resource://" + getPackageName() + "/"+R.raw.yg;
        mVideoView = findViewById(R.id.activity_my_sf);

        sdcardPath = Environment.getExternalStorageDirectory() +"/ad.mp4";
        tv_checksd = findViewById(R.id.tv_checksd);
        tv_tip = findViewById(R.id.tv_tip);

        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent turnIntent = new Intent(VideoActivity.this,MainActivity.class);
                startActivity(turnIntent);
                return false;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getGunAddOilStatus(GunAddOilStatusInfo gunAddOilStatusInfo) {
        System.out.println("yanyan status="+gunAddOilStatusInfo.getGunStaus());
        if(gunAddOilStatusInfo.getGunStaus()==3) return;
        if(gunAddOilStatusInfo.getMessageType()==10 && gunAddOilStatusInfo.getNpanel() == Integer.parseInt(oil_one_gun_number) && !MainFragment.breceiveDatapannel1) {
            enterOnePannel++;
            if (enterOnePannel == 1 && !MainFragment.breceiveDatapannel1) {
                videoOneUdp = false;
                Intent onePannelIntent = new Intent(VideoActivity.this, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt(Common.GUN_TYPE, Common.GUN_TYPE_ONE);
                bundle1.putInt(Common.GUN_TYPE_ONE_NUMBER, Integer.parseInt(oil_one_gun_number));
                startActivity(onePannelIntent);
            }
        }

        if(gunAddOilStatusInfo.getMessageType()==10 && gunAddOilStatusInfo.getNpanel() == Integer.parseInt(oil_two_gun_number) && !MainFragment.breceiveDatapannel2) {
            enterTwoPannel++;
            videoTwoUdp = false;
            if (enterTwoPannel == 1 && !MainFragment.breceiveDatapannel2) {
                Intent twoPannelIntent = new Intent(VideoActivity.this, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt(Common.GUN_TYPE, Common.GUN_TYPE_TWO);
                bundle1.putInt(Common.GUN_TYPE_TWO_NUMBER, Integer.parseInt(oil_two_gun_number));
                startActivity(twoPannelIntent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mVideoView.isPlaying()) {
            mVideoView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView.canPause()) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mVideoView) {
            try {
                mVideoView.stopPlayback();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mVideoView = null;
        }
        super.onDestroy();
    }
    public static final String STRPREFERENCE = "strPreference";
    @Override
    protected void onStart() {
        super.onStart();
        @SuppressLint("WrongConstant")
        SharedPreferences sharedPreferences = getSharedPreferences(STRPREFERENCE,MODE_APPEND);
        if(sharedPreferences.getString("onegunno","1")!="" ){
            oil_one_gun_number = sharedPreferences.getString("onegunno","1");

        }
        if(sharedPreferences.getString("onegunno","2")!="" ) {
            oil_two_gun_number = sharedPreferences.getString("twogunno", "2");
        }
        File file = new File(sdcardPath);//获取文件对象
        System.out.println("videoActivity file="+file.exists()+",mp4_path="+mp4_path);
        //file.exists()
        if (file.exists()) {//判断文件是否存在

            tv_checksd.setVisibility(View.GONE);
            tv_tip.setVisibility(View.GONE);
            Uri uri = Uri.parse(sdcardPath);//解析为URI地址对象
            MediaController mediaController = new MediaController(this);//创建控制器对象
            mediaController.setVisibility(View.GONE);//控制器隐藏
            mVideoView.setMediaController(mediaController);//设置控制器
            mVideoView.setVideoURI(uri);//设置播放器播放地址

            mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mVideoView.stopPlayback();
                    return false;
                }
            });

            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(true);
                }
            });

            //对象开始运行
            mVideoView.start();
        }else{
            tv_checksd.setVisibility(View.VISIBLE);
            tv_tip.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"请检查SD卡中是否存在视频文件",Toast.LENGTH_LONG).show();
        }
    }

}
