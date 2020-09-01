package com.xinkuai.globalsdk.sample;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.facebook.ads.AdSettings;
import com.xinkuai.globalsdk.XKGlobalSDK;

/**
 * Created by Long
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);//解决64k

        //初始化新快SDK
        XKGlobalSDK.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化新快SDK
        XKGlobalSDK.initialize(this);

        AdSettings.addTestDevice("f1445d1b-3576-4724-8530-fe29ae437a91");

    }
}
