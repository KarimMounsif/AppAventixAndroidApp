package com.example.km.qrcodepay.utilitaire;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by TVE on 18/03/2018.
 */

public class DelayerStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
