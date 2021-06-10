package com.zy.horizontalproject27;

import android.app.Application;
import android.content.Intent;

public class APP extends Application {
    Intent serviceIntent = null;
    @Override
    public void onCreate() {
        super.onCreate();

        serviceIntent = new Intent(this, UdpService.class);
        startService(serviceIntent);
    }


}
